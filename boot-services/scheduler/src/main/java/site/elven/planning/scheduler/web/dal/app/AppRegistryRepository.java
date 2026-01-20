package site.elven.planning.scheduler.web.dal.app;

import org.springframework.stereotype.Repository;
import site.elven.planning.boot.base.web.dal.base.BaseRepository;
import site.elven.planning.scheduler.web.model.entity.app.AppRegistry;

@Repository
public interface AppRegistryRepository extends BaseRepository<AppRegistry, Long> {
}
