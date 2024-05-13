package edu.hitwh.homework.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import edu.hitwh.homework.mapper.ClsMapper;
import edu.hitwh.homework.pojo.Cls;
import edu.hitwh.homework.pojo.PageCls;
import edu.hitwh.homework.service.ClsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class clsService implements ClsService {
    @Autowired
    private ClsMapper clsMapper;

    @Override
    public PageCls page(Integer page, Integer pageSize, String className, LocalDate begin, LocalDate end){
        PageHelper.startPage(page,pageSize);
        List<Cls> clsList = clsMapper.list(className, begin, end);
        Page<Cls> p = (Page<Cls>) clsList;
        PageCls pageCls = new PageCls(p.getTotal(), p.getResult());
        return pageCls;
    }

    @Override
    public void delete(Integer id) {
        clsMapper.delete(id);
    }

    @Override
    public void save(Cls cls) {
        clsMapper.save(cls);
    }

    @Override
    public Cls getById(Integer id) {
        return clsMapper.getById(id);
    }

    @Override
    public void update(Cls cls) {
        clsMapper.update(cls);
    }

    @Override
    public List<Cls> getAllCls(){
        return clsMapper.getAllCls();
    }
}
