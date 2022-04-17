package com.dlrtn.websocket.chat.model.payload;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class PassWordUpdateRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String existingPassword;

    @NotBlank
    private String newPassword;

    private LocalDateTime updatedAt;

}
