package de.roamingthings.tracing.novellibrary.ports.driven;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Novel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbNovel {
    @Id
    private UUID uuid;

    private LocalDateTime created;

    private LocalDateTime authored;

    private String title;

    @Lob
    private String content;
}
