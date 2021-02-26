package za.luna;

import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@ApplicationScoped
public class DemoService {
    public List<Grass> getAll() {
        return Grass.listAll();
    }

    @Transactional
    public Grass create(Grass grass) {
        grass.persistAndFlush();
        return grass;
    }

    @Transactional
    public Grass remove(Grass grass) {
        Grass.deleteById(grass.id);
        return grass;
    }
}
