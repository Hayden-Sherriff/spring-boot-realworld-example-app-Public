package io.spring.application;

import io.spring.infrastructure.mybatis.readservice.TagReadService;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagsQueryService {
  private TagReadService tagReadService;

  public List<String> allTags() {
    return tagReadService.all();
  }

  public List<String> allTagsSorted(String sortBy) {
    List<String> tags = tagReadService.all();
    
    if ("name_desc".equals(sortBy)) {
        Collections.sort(tags, Collections.reverseOrder());
    } else if ("name_asc".equals(sortBy)) {
        Collections.sort(tags);
    } else {
        Collections.sort(tags); 
    
    return tags;
  }
}
