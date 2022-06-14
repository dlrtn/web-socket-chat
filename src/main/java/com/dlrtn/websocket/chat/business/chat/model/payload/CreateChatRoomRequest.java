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
public class CreateChatRoomRequest {

    @NotBlank
    private final String chatId;

    @NotBlank
    private final String chatName;

    @NotBlank
    private final ChatType chatType;

    private final List<String> chatMembers;

}
