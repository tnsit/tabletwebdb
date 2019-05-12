package com.example.tabletwebdb.rest;

import com.example.tabletwebdb.Enum.Place;
import com.example.tabletwebdb.entity.*;
import com.example.tabletwebdb.repos.*;
import com.example.tabletwebdb.responce.Response;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RestExcel {
    @Autowired
    private TabletRepo tabletRepo;

    @Autowired
    private ModelTabletRepo modelTabletRepo;

    @Autowired
    private OsRepo osRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private AkbRepo akbRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Value("${upload.path}")
    private String uploadpath;

    @PostMapping("/tabletExcel")
    public Response tabletExcel (){
        return getFile("Планшет", null);
    }

    @PostMapping("/akbExcel")
    public Response akbExcel (){
        return getFile("Акб", null);
    }

    @PostMapping("/transactionExcel")
    public Response transactionExcel (){
        return getFile("Транзакции", null);
    }

    @PostMapping("/managerHistoryExcel")
    public Response managerHistoryExcel (@AuthenticationPrincipal User user){
        return getFile("Менеджер", user);
    }

    private Response getFile(String device, User user){
        Map<String, String> response = new HashMap<>();

        File uploadDir = new File(uploadpath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String sdf = " dd.MM.yyyy_HH.mm.ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sdf);
        Date date = new Date();
        String resultFilename = "";
        if (device.equals("Планшет")){
            resultFilename = "tablets_"+simpleDateFormat.format(date)+".xlsx";
        }
        if (device.equals("Акб")){
            resultFilename = "akb_"+simpleDateFormat.format(date)+".xlsx";
        }
        if (device.equals("Транзакции")){
            resultFilename = "transaction_"+simpleDateFormat.format(date)+".xlsx";
        }
        if (device.equals("Менеджер")){
            resultFilename = "history_"+simpleDateFormat.format(date)+".xlsx";
        }

        try {
            File fileTablet = new File(uploadpath + "/" + resultFilename);
            FileOutputStream file = new FileOutputStream(fileTablet);
            if (device.equals("Планшет")){
                getWorkbook("Планшет", null).write(file);
            }
            if (device.equals("Транзакции")){
                getWorkbook("Транзакции", null).write(file);
            }
            if (device.equals("Акб")){
                getWorkbook("Акб", null).write(file);
            }
            if (device.equals("Менеджер")){
                getWorkbook("Менеджер", user).write(file);
            }

            file.close();
            response.put("url", "/files/"+fileTablet.getName());
            return new Response("Done", response);
        }
        catch (Exception e){
            response.put("error", e.toString());
            return new Response("Error", response);
        }

    }

    private Workbook getWorkbook(String device, User user){
        Workbook workbook = new XSSFWorkbook();
        if (device.equals("Планшет")){
            Sheet sheet = workbook.createSheet("Планшет");
            Row row = sheet.createRow(0);
            sheet.createFreezePane(0,1);

            row.createCell(0).setCellValue("Номер");
            row.createCell(1).setCellValue("Модель");
            row.createCell(2).setCellValue("Состояние");
            row.createCell(3).setCellValue("Комментарий к статусу");
            row.createCell(4).setCellValue("IMEI");
            row.createCell(5).setCellValue("Версия OS");
            row.createCell(6).setCellValue("Телефон");
            row.createCell(7).setCellValue("Пин");
            row.createCell(8).setCellValue("У кого");
            row.createCell(9).setCellValue("Город");
            row.createCell(10).setCellValue("Менеджер");
            row.createCell(11).setCellValue("Для чего");
            row.createCell(12).setCellValue("Интервьюер");
            row.createCell(13).setCellValue("Подрядчик");
            row.createCell(14).setCellValue("Дата транзакции");
            row.createCell(15).setCellValue("Ответственный за тр-ю");
            row.createCell(16).setCellValue("Дата инвентаризации");
            row.createCell(17).setCellValue("Основание");
            row.createCell(18).setCellValue("Комментарий к планшету");

            List<Tablet> tablets = tabletRepo.findAllByOrderByNumberAsc();

            for (int i=1; i<=tablets.size(); i++){
                Row row1 = sheet.createRow(i);
                row1.createCell(0).setCellValue(tablets.get(i-1).getNumber());
                row1.createCell(1).setCellValue(tablets.get(i-1).getModelTablet().getModelTablet());
                row1.createCell(2).setCellValue(tablets.get(i-1).getStatus().getStatus());

                if(tablets.get(i-1).getStatusComment()==null){
                    row1.createCell(3).setCellValue("");
                }
                else {
                    row1.createCell(3).setCellValue(tablets.get(i-1).getStatusComment());
                }

                row1.createCell(4).setCellValue(tablets.get(i-1).getImei());
                row1.createCell(5).setCellValue(tablets.get(i-1).getOs().getOs());
                row1.createCell(6).setCellValue(tablets.get(i-1).getPhone());
                row1.createCell(7).setCellValue(tablets.get(i-1).getPin());
                row1.createCell(8).setCellValue(tablets.get(i-1).getPlaceTablet().stream().findFirst().get().getName());

                if(tablets.get(i-1).getCity()==null){
                    row1.createCell(9).setCellValue("");
                }
                else {
                    row1.createCell(9).setCellValue(tablets.get(i-1).getCity().getCity());
                }

                if(tablets.get(i-1).getUser()==null){
                    row1.createCell(10).setCellValue("");
                }
                else {
                    row1.createCell(10).setCellValue(tablets.get(i-1).getUser().getName());
                }

                if(tablets.get(i-1).getPurpose()==null){
                    row1.createCell(11).setCellValue("");
                }
                else {
                    row1.createCell(11).setCellValue(tablets.get(i-1).getPurpose().getPurpose());
                }

                if(tablets.get(i-1).getInterviewer()==null){
                    row1.createCell(12).setCellValue("");
                }
                else {
                    row1.createCell(12).setCellValue(tablets.get(i-1).getInterviewer().getShortName());
                }

                if(tablets.get(i-1).getContractor()==null){
                    row1.createCell(13).setCellValue("");
                }
                else {
                    row1.createCell(13).setCellValue(tablets.get(i-1).getContractor().getCode());
                }

                if(tablets.get(i-1).getTransaction()==null){
                    row1.createCell(14).setCellValue("");
                    row1.createCell(15).setCellValue("");
                }
                else {
                    row1.createCell(14).setCellValue(tablets.get(i-1).getTransaction().getDate());
                    row1.createCell(15).setCellValue(tablets.get(i-1).getTransaction().getRespUser().getName());
                }



                row1.createCell(16).setCellValue(tablets.get(i-1).getInventoryDate());
                row1.createCell(17).setCellValue(tablets.get(i-1).getInventoryReason());

                if(tablets.get(i-1).getTabletComment()==null){
                    row1.createCell(18).setCellValue("");
                }
                else {
                    row1.createCell(18).setCellValue(tablets.get(i-1).getTabletComment());
                }

            }

            for (int i=0; i<19; i++){
                sheet.autoSizeColumn(i);
            }
        }

        if (device.equals("Акб")){
            Sheet sheet = workbook.createSheet("Акб");
            Row row = sheet.createRow(0);
            sheet.createFreezePane(0,1);

            row.createCell(0).setCellValue("Номер");
            row.createCell(1).setCellValue("Модель");
            row.createCell(2).setCellValue("Состояние");
            row.createCell(3).setCellValue("У кого");
            row.createCell(4).setCellValue("Город");
            row.createCell(5).setCellValue("Менеджер");
            row.createCell(6).setCellValue("Интервьюер");
            row.createCell(7).setCellValue("Подрядчик");
            row.createCell(8).setCellValue("Дата");
            row.createCell(9).setCellValue("Ответственный за тр-ю");

            List<Akb> akb = akbRepo.findAllByOrderByNumberAsc();

            for (int i=1; i<=akb.size(); i++){
                Row row1 = sheet.createRow(i);
                row1.createCell(0).setCellValue(akb.get(i-1).getNumber());
                row1.createCell(1).setCellValue(akb.get(i-1).getModelAkb().getModelAkb());
                row1.createCell(2).setCellValue(akb.get(i-1).getStatus().getStatus());
                row1.createCell(3).setCellValue(akb.get(i-1).getPlaceAkb().stream().findFirst().get().getName());

                if(akb.get(i-1).getCity()==null){
                    row1.createCell(4).setCellValue("");
                }
                else {
                    row1.createCell(4).setCellValue(akb.get(i-1).getCity().getCity());
                }

                if(akb.get(i-1).getUser()==null){
                    row1.createCell(5).setCellValue("");
                }
                else {
                    row1.createCell(5).setCellValue(akb.get(i-1).getUser().getName());
                }

                if(akb.get(i-1).getInterviewer()==null){
                    row1.createCell(6).setCellValue("");
                }
                else {
                    row1.createCell(6).setCellValue(akb.get(i-1).getInterviewer().getShortName());
                }

                if(akb.get(i-1).getContractor()==null){
                    row1.createCell(7).setCellValue("");
                }
                else {
                    row1.createCell(7).setCellValue(akb.get(i-1).getContractor().getCode());
                }

                row1.createCell(8).setCellValue(akb.get(i-1).getDate());

                if(akb.get(i-1).getRespUser()==null){
                    row1.createCell(9).setCellValue("");
                }
                else {
                    row1.createCell(9).setCellValue(akb.get(i-1).getRespUser().getName());
                }
            }

            for (int i=0; i<10; i++){
                sheet.autoSizeColumn(i);
            }
        }



        if (device.equals("Транзакции")){
            Sheet sheet = workbook.createSheet("Транзакции");
            Row row = sheet.createRow(0);
            sheet.createFreezePane(0,1);

            row.createCell(0).setCellValue("Номер");
            row.createCell(1).setCellValue("Тип");
            row.createCell(2).setCellValue("От пользователя");
            row.createCell(3).setCellValue("От подрядчика");
            row.createCell(4).setCellValue("От интервьюера");
            row.createCell(5).setCellValue("Пользователю");
            row.createCell(6).setCellValue("Подрядчику");
            row.createCell(7).setCellValue("Интервьюеру");
            row.createCell(8).setCellValue("Дата");
            row.createCell(9).setCellValue("Ответственный");

            List<Transaction> transactions = transactionRepo.findAll();

            for (int i=1; i<=transactions.size(); i++){
                Row row1 = sheet.createRow(i);
                row1.createCell(0).setCellValue(transactions.get(i-1).getTablet().getNumber());
                row1.createCell(1).setCellValue(transactions.get(i-1).getTransType().stream().findFirst().get().getName());


                if(transactions.get(i-1).getFromUser()==null){
                    row1.createCell(2).setCellValue("");
                }
                else {
                    row1.createCell(2).setCellValue(transactions.get(i-1).getFromUser().getName());
                }

                if(transactions.get(i-1).getFromContractor()==null){
                    row1.createCell(3).setCellValue("");
                }
                else {
                    row1.createCell(3).setCellValue(transactions.get(i-1).getFromContractor().getCode());
                }

                if(transactions.get(i-1).getFromInterviewer()==null){
                    row1.createCell(4).setCellValue("");
                }
                else {
                    row1.createCell(4).setCellValue(transactions.get(i-1).getFromInterviewer().getShortName());
                }

                if(transactions.get(i-1).getToUser()==null){
                    row1.createCell(5).setCellValue("");
                }
                else {
                    row1.createCell(5).setCellValue(transactions.get(i-1).getToUser().getName());
                }

                if(transactions.get(i-1).getToContractor()==null){
                    row1.createCell(6).setCellValue("");
                }
                else {
                    row1.createCell(6).setCellValue(transactions.get(i-1).getToContractor().getCode());
                }

                if(transactions.get(i-1).getToInterviewer()==null){
                    row1.createCell(7).setCellValue("");
                }
                else {
                    row1.createCell(7).setCellValue(transactions.get(i-1).getToInterviewer().getShortName());
                }
                row1.createCell(8).setCellValue(transactions.get(i-1).getDate());
                row1.createCell(9).setCellValue(transactions.get(i-1).getRespUser().getName());

            }

            for (int i=0; i<10; i++){
                sheet.autoSizeColumn(i);
            }
        }


        if (device.equals("Менеджер")){
            Sheet sheet = workbook.createSheet("История");
            Row row = sheet.createRow(0);
            sheet.createFreezePane(0,1);

            row.createCell(0).setCellValue("Номер");
            row.createCell(1).setCellValue("Тип");
            row.createCell(2).setCellValue("От пользователя");
            row.createCell(3).setCellValue("От интервьюера");
            row.createCell(4).setCellValue("Пользователю");
            row.createCell(5).setCellValue("Интервьюеру");
            row.createCell(6).setCellValue("Дата");
            row.createCell(7).setCellValue("Ответственный");

            List<Transaction> transactions = transactionRepo.findAllByFromUserOrToUser(user, user);

            for (int i=1; i<=transactions.size(); i++){
                Row row1 = sheet.createRow(i);
                row1.createCell(0).setCellValue(transactions.get(i-1).getTablet().getNumber());
                row1.createCell(1).setCellValue(transactions.get(i-1).getTransType().stream().findFirst().get().getName());


                if(transactions.get(i-1).getFromUser()==null){
                    row1.createCell(2).setCellValue("");
                }
                else {
                    row1.createCell(2).setCellValue(transactions.get(i-1).getFromUser().getName());
                }

                if(transactions.get(i-1).getFromInterviewer()==null){
                    row1.createCell(3).setCellValue("");
                }
                else {
                    row1.createCell(3).setCellValue(transactions.get(i-1).getFromInterviewer().getShortName());
                }

                if(transactions.get(i-1).getToUser()==null){
                    row1.createCell(4).setCellValue("");
                }
                else {
                    row1.createCell(4).setCellValue(transactions.get(i-1).getToUser().getName());
                }

                if(transactions.get(i-1).getToInterviewer()==null){
                    row1.createCell(5).setCellValue("");
                }
                else {
                    row1.createCell(5).setCellValue(transactions.get(i-1).getToInterviewer().getShortName());
                }
                row1.createCell(6).setCellValue(transactions.get(i-1).getDate());
                row1.createCell(7).setCellValue(transactions.get(i-1).getRespUser().getName());

            }

            for (int i=0; i<8; i++){
                sheet.autoSizeColumn(i);
            }
        }



        return workbook;
    }

    @PostMapping("/addFromExcel")
    public Response addFromExcel (@RequestPart("upload-file") MultipartFile uploadFile){
        String errorMessage = "";
        XSSFWorkbook tabletsExcel = null;
        ArrayList<String> numberColumn = new ArrayList<>();
        ArrayList<String> imeiColumn = new ArrayList<>();
        ArrayList<String> phoneColumn = new ArrayList<>();
        ArrayList<String> pinColumn = new ArrayList<>();
        ArrayList<String> modelColumn = new ArrayList<>();
        ArrayList<String> osColumn = new ArrayList<>();
        ArrayList<String> statusColumn = new ArrayList<>();
        ArrayList<String> statusCommentColumn = new ArrayList<>();
        ArrayList<String> tabletCommentColumn = new ArrayList<>();


        Set<String> duplicate = new LinkedHashSet<>();
        ArrayList<String> emptyOrWrong = new ArrayList<>();
        ArrayList<String> notFoundInDB = new ArrayList<>();
        Set<String> numberExists = new LinkedHashSet<>();
        Set<String> imeiExists = new LinkedHashSet<>();
        Set<String> phoneExists = new LinkedHashSet<>();

        try{
            tabletsExcel = new XSSFWorkbook(uploadFile.getInputStream());
        }
        catch (Exception e){

        }
        XSSFSheet sheet = tabletsExcel.getSheetAt(0);

        for (int i=1; i<=sheet.getLastRowNum();i++){
            XSSFRow row = sheet.getRow(i);
            if (row.getCell(0)==null||!row.getCell(0).getStringCellValue().matches("^\\d{4,}")){
                emptyOrWrong.add(i+1+"-"+"A");
            }
            else {
                if (tabletRepo.findByNumber(row.getCell(0).getStringCellValue())!=null){
                    numberExists.add(row.getCell(0).getStringCellValue());
                }
                else {
                    numberColumn.add(row.getCell(0).getStringCellValue());
                }

            }

            if (row.getCell(1)==null||(!row.getCell(1).getStringCellValue().equals("-")&&!row.getCell(1).getStringCellValue().matches("^\\d{15,}"))){
                emptyOrWrong.add(i+1+"-"+"B");
            }
            else {
                if (tabletRepo.findByImei(row.getCell(1).getStringCellValue())!=null){
                    imeiExists.add(row.getCell(1).getStringCellValue());
                }
                imeiColumn.add(row.getCell(1).getStringCellValue());
            }

            if (row.getCell(2)==null||(!row.getCell(2).getStringCellValue().equals("-")&&!row.getCell(2).getStringCellValue().matches("^\\d{10}"))){
                emptyOrWrong.add(i+1+"-"+"C");
            }
            else {
                if (tabletRepo.findByPhone(row.getCell(2).getStringCellValue())!=null){
                    phoneExists.add(row.getCell(2).getStringCellValue());
                }
                phoneColumn.add(row.getCell(2).getStringCellValue());
            }

            if (row.getCell(3)==null||(!row.getCell(3).getStringCellValue().equals("-")&&!row.getCell(3).getStringCellValue().matches("^\\d{4}"))){
                emptyOrWrong.add(i+1+"-"+"D");
            }
            else {
                pinColumn.add(row.getCell(3).getStringCellValue());
            }

            if(row.getCell(4)==null){
                emptyOrWrong.add(i+1+"-"+"E");
            }
            else {
                if (modelTabletRepo.findByModelTablet(row.getCell(4).getStringCellValue())==null){
                    notFoundInDB.add(i+1+"-"+"E");
                }
                else {
                    modelColumn.add(row.getCell(4).getStringCellValue());
                }
            }

            if(row.getCell(5)==null){
                emptyOrWrong.add(i+1+"-"+"F");
            }
            else {
                if (osRepo.findByOs(row.getCell(5).getStringCellValue())==null){
                    notFoundInDB.add(i+1+"-"+"F");
                }
                else {
                    osColumn.add(row.getCell(5).getStringCellValue());
                }
            }

            if(row.getCell(6)==null){
                emptyOrWrong.add(i+1+"-"+"G");
            }
            else {
                if (statusRepo.findByStatus(row.getCell(6).getStringCellValue())==null){
                    notFoundInDB.add(i+1+"-"+"G");
                }
                else {
                    statusColumn.add(row.getCell(6).getStringCellValue());
                }
            }


            if(row.getCell(7)==null){
                statusCommentColumn.add(null);
            }
            else {
                statusCommentColumn.add(row.getCell(7).getStringCellValue());
            }

            if(row.getCell(8)==null){
                tabletCommentColumn.add(null);
            }
            else {
                tabletCommentColumn.add(row.getCell(8).getStringCellValue());
            }
        }

        for (int i=0; i<numberColumn.size(); i++){
            for (int j=i+1; j<numberColumn.size(); j++){
                if (numberColumn.get(i).equals(numberColumn.get(j))){
                    duplicate.add(numberColumn.get(i));
                    break;
                }
            }
        }

        if (!numberExists.isEmpty()){
            StringBuilder numberExistsString = new StringBuilder("С таким номером уже существуют:</br>");
            for (String s: numberExists){
                numberExistsString.append(s+", ");
            }
            numberExistsString.setLength(numberExistsString.length()-2);
            System.out.println(numberExistsString);
            errorMessage=errorMessage+numberExistsString.toString()+"</br>";
        }

        if (!imeiExists.isEmpty()){
            StringBuilder imeiExistsString = new StringBuilder("С таким IMEI уже существуют:</br>");
            for (String s: imeiExists){
                imeiExistsString.append(s+", ");
            }
            imeiExistsString.setLength(imeiExistsString.length()-2);
            System.out.println(imeiExistsString);
            errorMessage=errorMessage+imeiExistsString.toString()+"</br>";
        }

        if (!phoneExists.isEmpty()){
            StringBuilder phoneExistsString = new StringBuilder("С таким телефоном уже существуют:</br>");
            for (String s: phoneExists){
                phoneExistsString.append(s+", ");
            }
            phoneExistsString.setLength(phoneExistsString.length()-2);
            System.out.println(phoneExistsString);
            errorMessage=errorMessage+phoneExistsString.toString()+"</br>";
        }

        if (!duplicate.isEmpty()){
            StringBuilder duplicateString = new StringBuilder("Дубликаты в файле:</br>");
            for (String s: duplicate){
                duplicateString.append(s+", ");
            }
            duplicateString.setLength(duplicateString.length()-2);
            System.out.println(duplicateString);
            errorMessage=errorMessage+duplicateString.toString()+"</br>";
        }

        if (!emptyOrWrong.isEmpty()){
            StringBuilder emptyString = new StringBuilder("Пустые или неверный формат:</br>");
            for (String s: emptyOrWrong){
                emptyString.append(s+", ");
            }
            emptyString.setLength(emptyString.length()-2);
            System.out.println(emptyString);
            errorMessage=errorMessage+emptyString.toString()+"</br>";
        }

        if (!notFoundInDB.isEmpty()){
            StringBuilder notFoundString = new StringBuilder("Не найдены в базе:</br>");
            for (String s: notFoundInDB){
                notFoundString.append(s+", ");
            }
            notFoundString.setLength(notFoundString.length()-2);
            System.out.println(notFoundString);
            errorMessage=errorMessage+notFoundString.toString()+"</br>";
        }
        Map<String, String> response = new HashMap<>();
        if(!errorMessage.equals("")){
            response.put("message", errorMessage);
            return new Response("Error", response);
        }
        else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Set<Place> placeTablet = Collections.singleton(Place.WAREHOUSE);
            for (int i=0; i<numberColumn.size();i++){
                Tablet tablet = new Tablet(numberColumn.get(i),
                        modelTabletRepo.findByModelTablet(modelColumn.get(i)),
                        statusRepo.findByStatus(statusColumn.get(i)),
                        statusCommentColumn.get(i),
                        imeiColumn.get(i),
                        osRepo.findByOs(osColumn.get(i)),
                        phoneColumn.get(i),
                        pinColumn.get(i),
                        placeTablet,
                        cityRepo.findByCity("Москва"),
                        sdf.format(date),
                        "добавление в базу",
                        tabletCommentColumn.get(i));
                tabletRepo.save(tablet);
            }

            response.put("message", "Планшетов добавлено в базу: "+numberColumn.size());
            return new Response("Done", response);
        }



    }
}
