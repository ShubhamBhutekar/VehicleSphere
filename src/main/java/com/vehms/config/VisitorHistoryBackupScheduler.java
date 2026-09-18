package com.vehms.config;

import com.vehms.entity.Resident;
import com.vehms.entity.Visitor;
import com.vehms.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// TICKET: VEHMS-M02-T031
// Runs every night at 11:00 PM, pulls the day's visitor records, and writes them
// to an .xlsx file in a "visitors log" folder (auto-created if missing).
@Slf4j
@Component
@RequiredArgsConstructor
public class VisitorHistoryBackupScheduler {

    private final VisitorRepository visitorRepository;

    // Configurable in application.properties; defaults to a folder next to where the app runs.
    @Value("${vehms.visitor-backup.directory:visitors log}")
    private String backupDirectory;

    private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyyyy");

    @Scheduled(cron = "0 0 23 * * *") // every night at 11:00 PM
    public void backupTodaysVisitors() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        List<Visitor> visitors = visitorRepository.findByTimeInBetween(startOfDay, endOfDay);

        File folder = new File(backupDirectory);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            log.info("Created visitor backup folder [{}]: {}", folder.getAbsolutePath(), created);
        }

        String fileName = "Visitors_history_log_" + today.format(FILE_DATE_FORMAT) + ".xlsx";
        File outputFile = new File(folder, fileName);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Visitors");

            String[] headers = {
                    "Flat No", "Resident Name", "Visitor Name", "Vehicle Name",
                    "Vehicle Registration Number", "Visit Purpose", "Time In",
                    "Time Out", "Phone Number", "Visitor Type", "Visit Duration"
            };
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (Visitor visitor : visitors) {
                Resident resident = visitor.getResident();
                Row row = sheet.createRow(rowIdx++);
                writeCell(row, 0, resident != null ? resident.getFlatNo() : "");
                writeCell(row, 1, resident != null ? (resident.getFName() + " " +
                        (resident.getLName() != null ? resident.getLName() : "")).trim() : "");
                writeCell(row, 2, visitor.getVisitorName());
                writeCell(row, 3, visitor.getVehicleName());
                writeCell(row, 4, visitor.getVehicleRegistrationNumber());
                writeCell(row, 5, visitor.getVisitPurpose());
                writeCell(row, 6, String.valueOf(visitor.getTimeIn()));
                writeCell(row, 7, String.valueOf(visitor.getTimeOut()));
                writeCell(row, 8, visitor.getPhoneNumber() != null ? String.valueOf(visitor.getPhoneNumber()) : "");
                writeCell(row, 9, visitor.getVisitorType() != null ? visitor.getVisitorType().name() : "");
                writeCell(row, 10, visitor.getVisitDuration());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                workbook.write(fos);
            }
            log.info("Visitor history backup written: {} ({} records)", outputFile.getAbsolutePath(), visitors.size());

        } catch (IOException e) {
            log.error("Failed to write visitor history backup to {}", outputFile.getAbsolutePath(), e);
        }
    }

    private void writeCell(Row row, int index, String value) {
        Cell cell = row.createCell(index);
        cell.setCellValue(value != null ? value : "");
    }
}
