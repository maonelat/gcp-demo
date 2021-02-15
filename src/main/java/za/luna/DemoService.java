package za.luna;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

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
