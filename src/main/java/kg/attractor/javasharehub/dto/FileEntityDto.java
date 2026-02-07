package kg.attractor.javasharehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileEntityDto {
    private Long id;
    private String name;
    private String path;
    private String status;
    private Long downloadCount;
}
