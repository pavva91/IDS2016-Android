package com.emergencyescape.text;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */



import com.emergencyescape.DBHelper;
import com.emergencyescape.MyApplication;
import com.emergencyescape.businesslogic.ServerConnection;
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
public class TextDeparturePresenter extends CommonBehaviourPresenter<TexterView> implements TextDeparturePresenterInterface {
    private DaoSession daoSession = MyApplication.getSession();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private UserDao userDao = daoSession.getUserDao();
    private SessionClass sessionClass = SessionClass.getInstance();
    private String userName = sessionClass.getUser(MyApplication.context);
    private String token = sessionClass.getServerKey(MyApplication.context);
    ServerConnection serverConnection = ServerConnection.getInstance(MyApplication.context);

    @Override
    public ArrayList<String> getNodesList(){
        ArrayList<String> allNames = new ArrayList<>();
        List<Node> allNodes = nodeDao.loadAll();
        for (Node singleNode : allNodes) {
            String singleName = singleNode.getCode().toString();
            allNames.add(singleName);
        }
        return allNames;
    }

    @Override
    public void setUserDeparture(String departure) { // TODO: Aggiornare anche il Server
        List<User> allUser = userDao.loadAll();

        for (User singleUser : allUser) {
            if(singleUser.getName().equals(userName)){
                singleUser.setDepartureId(this.getDepartureIdFromName(departure));
                userDao.update(singleUser);
                serverConnection.updateUserPosition(this.getDepartureIdFromName(departure).toString(),userName,token);
            }
        }
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

}
