package com.emergencyescape.text;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */



import com.emergencyescape.MyApplication;
import com.emergencyescape.businesslogic.SessionClass;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape.text
 * TextDeparturePresenter
 */
public class TextDestinationPresenter extends CommonBehaviourPresenter<TexterView> implements TextDestinationPresenterInterface {
    private DaoSession daoSession = MyApplication.getSession();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private UserDao userDao = daoSession.getUserDao();

    private SessionClass sessionClass = SessionClass.getInstance();
    private String userName = sessionClass.getUser(MyApplication.context); // userName from SharedPreferences

    @Override
    public ArrayList<String> getNodesList(){
        ArrayList<String> allNames = new ArrayList<String>();
        List<Node> allNodes = nodeDao.loadAll();
        for (Node singleNode : allNodes) {
            String singleName = singleNode.getCode().toString();
            allNames.add(singleName);
        }
        return allNames;
    }

    @Override
    public Long getDepartureIdFromName(String departureName){
        List<Node> allNodes = nodeDao.loadAll();
        Long departureId = -1L;
        for (Node singleNode : allNodes) {
            if(singleNode.getCode().equalsIgnoreCase(departureName)){
                departureId = singleNode.getId();
            }
        }
        return departureId;
    }

    @Override
    public void setUserDestination(String destination) {
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase(userName)){
                singleUser.setDestinationId(this.getDestinationIdFromName(destination));
                userDao.update(singleUser);
            }
        }
    }

    @Override
    public Long getDestinationIdFromName(String destinationName){
        List<Node> allNodes = nodeDao.loadAll();
        Long destinationId = -1L;
        for (Node singleNode : allNodes) {
            if(singleNode.getCode().equalsIgnoreCase(destinationName)){
                destinationId = singleNode.getId();
            }
        }
        return destinationId;
    }

    @Override
    public String getUserDeparture() {
        String userDeparture = "";
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase(userName)){
                userDeparture = singleUser.getDepartureToOneUser().getCode();
            }
        }
        return userDeparture;
    }
}
