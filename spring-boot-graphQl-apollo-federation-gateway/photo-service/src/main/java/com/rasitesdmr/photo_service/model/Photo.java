package com.rasitesdmr.photo_service.model;


import com.rasitesdmr.photo_service.domain.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "photos")
public class Photo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private Long userId;

    @Override
    public String toString() {
        return String.format("PHOTO ID : %s", this.id);
    }
}
