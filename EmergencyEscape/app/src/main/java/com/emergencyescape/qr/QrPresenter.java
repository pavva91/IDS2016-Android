package com.emergencyescape.qr;
/**
 * Created by Valerio Mattioli on 24/05/2016.
 */

import com.emergencyescape.MyApplication;
import com.emergencyescape.commonbehaviour.CommonBehaviourPresenter;
import com.emergencyescape.greendao.DaoSession;
import com.emergencyescape.greendao.Node;
import com.emergencyescape.greendao.NodeDao;
import com.emergencyescape.greendao.User;
import com.emergencyescape.greendao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * com.emergencyescape.qr
 * QrPresenter
 */
public class QrPresenter extends CommonBehaviourPresenter<QrView> {
    private DaoSession daoSession = MyApplication.getSession();
    private NodeDao nodeDao = daoSession.getNodeDao();
    private UserDao userDao = daoSession.getUserDao();

    public void setUserDeparture(String departure) { // TODO: Aggiornare anche il Server
        List<User> allUser = userDao.loadAll();
        for (User singleUser : allUser) {
            if(singleUser.getName().equalsIgnoreCase("vale")){
                singleUser.setDepartureId(this.getDepartureIdFromName(departure));
                userDao.update(singleUser);
            }
        }
    }

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

    public ArrayList<String> getNodesList(){
        ArrayList<String> allNames = new ArrayList<String>();
        List<Node> allNodes = nodeDao.loadAll();
        for (Node singleNode : allNodes) {
            String singleName = singleNode.getCode().toString();
            allNames.add(singleName);
        }
        return allNames;
    }

}
