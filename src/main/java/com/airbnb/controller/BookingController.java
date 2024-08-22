package com.airbnb.controller;

import com.airbnb.dto.BookingDto;
import com.airbnb.entity.Booking;
import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.BookingRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.service.BucketService;
import com.airbnb.service.EmailService;
import com.airbnb.service.PDFService;
import com.airbnb.service.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private PDFService pdfService;
    private BucketService bucketService;
    private SmsService smsService;
    private EmailService emailService;

    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, PDFService pdfService, BucketService bucketService, SmsService smsService, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
        this.smsService = smsService;
        this.emailService = emailService;
    }

    @PostMapping("/createBooking/{propertyId}")
    public  ResponseEntity<?> createBooking(
            @RequestBody Booking booking,
            @AuthenticationPrincipal PropertyUser user,
            @PathVariable long propertyId) throws IOException {

        booking.setPropertyUser(user);

        Property property = propertyRepository.findById(propertyId).get();
        Integer propertyPrice = property.getNightlyPrice();
        Integer totalNights = booking.getTotalNights();
        int totalPrice = propertyPrice * totalNights;

        booking.setProperty(property);
        booking.setTotalPrice(totalPrice);

        Booking createdBooking = bookingRepository.save(booking);

        BookingDto dto=new BookingDto();
        dto.setBookingId(createdBooking.getId());
        dto.setGuestName(createdBooking.getGuestName());
        dto.setPrice(propertyPrice);
        dto.setTotalPrice(createdBooking.getTotalPrice());


        //Create PDF with Booking Conformation
        boolean b = pdfService.generatePDF("E://PDF//" + " booking-confirmation-id" + createdBooking.getId() + ".pdf", dto);
        if(b){
               //Upload the file into AmazonS3 bucket.

            String filePath = "E://PDF//" + "booking-confirmation-id" + createdBooking.getId() + ".pdf";
            //Here I convert the file to multipart file so that it can upload in AmazonS3 bucket.
            MultipartFile multipartFile = FileUtil.convertFileToMultipartFile(filePath);
            String uploadedFileUrl = bucketService.uploadFile(multipartFile, "myairbnb4");

             //After upload the file into the bucket for conformation of the ticket sms to the Costumer.
            Long phoneNumber=booking.getPhoneNumber();
            smsService.sendSms("phoneNumber","Your booking is confirmed.Click for download your ticket:" + uploadedFileUrl );
            emailService.sendEmail("demo@gmail.com","Your booking is confirmed.Click for download your ticket:","uploadedFileUrl");
        }else{
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    public class FileUtil {
        public static MultipartFile convertFileToMultipartFile(String filePath) throws IOException {
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/octet-stream", fileInputStream);
            return multipartFile;
        }
    }
}
