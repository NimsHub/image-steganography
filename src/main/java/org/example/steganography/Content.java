package org.example.steganography;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Content {
    private String key;
    private String message;
}
