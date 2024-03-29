package com.dlrtn.websocket.chat.business.chat.model.payload;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class CreateChatRequest {

    @NotBlank
    private final String chatRoomName;

    @NotBlank
    private final ChatType chatType;

    @NotBlank
    private final String chatRoomPassword;

    private final List<String> chatMemberIds;

}
