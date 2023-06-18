package org.example.user.attribute;

import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class DefaultUserLevelUpgradePolicy implements UserLevelUpgradePolicy{

    private IUserDao userDao;

    private static final int MIN_LOGIN_COUNT_FOR_SILVER = 50;

    private static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(IUserDao userDao){
        this.userDao = userDao;
    }


    public boolean canUpgradeLevel(User user){
        Level currentLevel = user.getLevel();

        switch(currentLevel){
            case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
            case PLATINUM:
                return false;
            default: throw new IllegalArgumentException("Unknown Level :"+currentLevel);
        }
    }

    public String upgradeLevel(User user){
        user.upgradeLevel();
        userDao.update(user);
        sendUpgradeMail(user);
        return user.getId();
    }

    private void sendUpgradeMail(User user) {
        Properties props = new Properties();
        props.put("mail.host", "smtp.naver.com");
        props.put("mail.port", "465");
        props.put("mail.smtp.auth" , "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.naver.com");
        props.put("mail.debug", "true");

        Session s = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("email","password");
            }
        });

        MimeMessage message = new MimeMessage(s);

        try{
            message.setFrom(new InternetAddress("tlatmsrud@naver.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Upgrade 안내");
            message.setText("사용자님의 등급이 " + user.getLevel().name() +" 로 업그레이드 되었습니다.");
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
