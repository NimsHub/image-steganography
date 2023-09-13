package org.example.steganography;

import lombok.RequiredArgsConstructor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static org.example.Constants.BYTE_SIZE;

@RequiredArgsConstructor
public class LSBSteganography implements SteganographyService {


    @Override
    public BufferedImage embedContent(String message, BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        ArrayList<Integer> messageBits = getMessageBits(message);
        int numOfBits = messageBits.size();

        int pixelIndex = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (pixelIndex < numOfBits + BYTE_SIZE * 4) {
                    int pixel = image.getRGB(x, y);

                    int red = (pixel >> BYTE_SIZE * 2) & 0xFF;
                    int green = (pixel >> BYTE_SIZE) & 0xFF;
                    int blue = pixel & 0xFF;

                    if (pixelIndex < BYTE_SIZE * 4) {
                        red = (red & 0xFE) | numOfBits >> (31 - pixelIndex);
                    } else {
                        int bitToHide = messageBits.get(pixelIndex - BYTE_SIZE * 4);
                        red = (red & 0xFE) | bitToHide;
                    }
                    image.setRGB(x, y, (red << BYTE_SIZE * 2) | (green << BYTE_SIZE) | blue);
                    pixelIndex++;
                } else {
                    break;
                }
            }
        }
        return image;
    }

    @Override
    public Content extractContent(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<Integer> keyCharList = new ArrayList<>();
        ArrayList<Integer> numOfBitsArray = new ArrayList<>();

        int pixelIndex = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int bitValue = (pixel >> BYTE_SIZE * 2) & 0x01;
                if (pixelIndex < BYTE_SIZE * 4) {
                    numOfBitsArray.add(bitValue);
                } else if (pixelIndex >= BYTE_SIZE * 4 && pixelIndex < BYTE_SIZE * 48) {
                    keyCharList.add(bitValue);
                } else if (getNumOfBits(numOfBitsArray) + BYTE_SIZE * 4 > pixelIndex) {
                    arrayList.add(bitValue);
                }
                pixelIndex++;
            }
        }

        ArrayList<Byte> messageBytes = getExtractedBytes(arrayList);
        ArrayList<Byte> keyBytes = getExtractedBytes(keyCharList);

        return Content.builder()
                .key(getExtractedString(keyBytes))
                .message(getExtractedString(messageBytes))
                .build();
    }




    private String getExtractedString(ArrayList<Byte> messageBytes) {
        byte[] byteArray = new byte[messageBytes.size()];
        for (int i = 0; i < messageBytes.size(); i++) {
            byteArray[i] = messageBytes.get(i);
        }

        return new String(byteArray);
    }

    private ArrayList<Byte> getExtractedBytes(ArrayList<Integer> arrayList) {

        ArrayList<Byte> messageBytes = new ArrayList<>();
        byte charByte = 0;
        int bitIndex = 7;

        for (int i : arrayList) {
            charByte |= (byte) ((i & 1) << bitIndex);
            bitIndex--;

            if (bitIndex < 0) {
                messageBytes.add(charByte);
                bitIndex = 7;
                charByte = 0;
            }
        }
        return messageBytes;
    }

    private ArrayList<Integer> getMessageBits(String message) {
        byte[] msgBytes = message.getBytes();
        ArrayList<Integer> arrayList = new ArrayList<>();

        for (byte b : msgBytes
        ) {
            for (int i = 7; i >= 0; i--) {
                int bitValue = (b >> i) & 0X01;
                arrayList.add(bitValue);
            }
        }
        return arrayList;
    }

    private int getNumOfBits(ArrayList<Integer> numOfBitsList) {
        int numOfChars = 0;
        int bitIndex = 31;
        for (int i : numOfBitsList) {
            numOfChars |= ((i & 1) << bitIndex);
            bitIndex--;
        }
        return numOfChars;
    }
}
