package com.gdsc.festivalholic.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/qrcode")
@Slf4j
public class QRCodeController {

    @GetMapping()
    public ResponseEntity<byte[]> generateQRCode() {
        final int width = 200;
        final int height = 200;
        final String url = "http://3.34.177.220:8083/hello";

        try {
            BitMatrix encodedQRCode = createQRCode(url, BarcodeFormat.QR_CODE, width, height);
            byte[] qrCodeImage = getQRCodeImage(encodedQRCode, "PNG");

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCodeImage);

        } catch (WriterException e) {
            log.error("Failed to generate QR code, {}", e.getMessage());
        } catch (IOException e) {
            log.error("Failed to convert QR code to image, {}", e.getMessage());
        }

        return ResponseEntity.badRequest().build();
    }

    private BitMatrix createQRCode(String url, BarcodeFormat format, int width, int height) throws WriterException {
        return new MultiFormatWriter().encode(url, format, width, height);
    }

    private byte[] getQRCodeImage(BitMatrix encodedQRCode, String imageFormat) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(encodedQRCode, imageFormat, outputStream);
        return outputStream.toByteArray();
    }
}
