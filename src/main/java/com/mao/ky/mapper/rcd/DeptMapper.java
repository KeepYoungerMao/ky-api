package com.mao.ky.mapper.rcd;

import com.mao.ky.entity.rcd.dept.DeptDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : create by zongx at 2020/12/3 17:45
 */
@Repository
@Mapper
public interface DeptMapper {

    List<DeptDo> getAllDept();

    DeptDo getDeptById(@Param("id") long id);

    void saveDept(DeptDo deptDo);

    int updateDept(DeptDo deptDo);

    int deleteDept(@Param("id") long id);

}
