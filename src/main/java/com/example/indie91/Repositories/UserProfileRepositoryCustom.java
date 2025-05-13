package com.example.indie91.Repositories;

import com.example.indie91.DTO.UserFullProfileDTO;
import java.util.List;
import java.util.UUID;

public interface UserProfileRepositoryCustom {
    List<UserFullProfileDTO> findUserFullProfile(UUID userId);
}
