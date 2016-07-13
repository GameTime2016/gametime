package com.sy.gametime.dao;
import java.util.List;

import com.sy.gametime.pojo.Collectors;
public interface CollectorsDao {
    public void setAddCollectors(Collectors collectors);
    public List<Collectors> selectCollectors(String code);
    public void deleteCollectors(Collectors temp);
}
