package com.rasitesdmr.likeservice.model;

import com.rasitesdmr.likeservice.domain.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "likes")
public class Like extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long photoId;

    @Override
    public String toString() {
        return String.format("LIKE ID : %s", this.id);
    }
}
