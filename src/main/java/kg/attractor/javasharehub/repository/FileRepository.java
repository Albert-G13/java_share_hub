package kg.attractor.javasharehub.repository;

import kg.attractor.javasharehub.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByDownloadToken(String token);

    List<FileEntity> findAllByUser_Email(String userEmail);

    List<FileEntity> findAllByStatusAndCategory_Id(String status, Long categoryId);

    List<FileEntity> findAllByStatus(String status);

}
