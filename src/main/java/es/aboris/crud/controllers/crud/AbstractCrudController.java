package es.aboris.crud.controllers.crud;

import es.aboris.crud.model.AbstractEntity;
import es.aboris.crud.services.AbstractService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public abstract class AbstractCrudController<T extends AbstractEntity, ID> {

    protected AbstractService<T, ID> service;

    protected AbstractCrudController(AbstractService<T, ID> service) {
        this.service = service;
    }

    @PostMapping("/save")
    public T save(@RequestBody T entity) {
        return service.save(entity);
    }

    @DeleteMapping("/delete/id/{id}")
    public void delete(@PathVariable ID id) {
        service.delete(id);
    }

    @GetMapping("/id/{id}")
    public T findById(@PathVariable ID id) {
        return (T) service.findById(id);
    }

    @GetMapping("/all")
    public List<T> findAll() {
        return (List<T>) service.findAll();
    }
}
