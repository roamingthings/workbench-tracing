package de.roamingthings.tracing.novellibrary.ports.driven;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NovelRepository extends JpaRepository<DbNovel, UUID> {
}
