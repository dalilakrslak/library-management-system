package ba.unsa.etf.system_events.dao.repository;

import ba.unsa.etf.system_events.dao.model.SystemEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemEventRepository extends JpaRepository<SystemEvent, Long> {
}
