package cn.chen.bos.service;

import cn.chen.bos.domain.bc.Subarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by hasee on 2017/7/22.
 */
public interface SubareaService {
    public void save(Subarea model);

    public Page<Subarea> pageQuery(PageRequest pageRequest);

    Page<Subarea> pageQuery(PageRequest pageRequest, Specification<Subarea> specification);

    List<Subarea> findAllBySpecification(Specification<Subarea> specification);

    List<Subarea> noassociation();
}
