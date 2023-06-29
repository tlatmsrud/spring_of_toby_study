package org.example.user.attribute;

import org.example.user.dao.IUserDao;
import org.example.user.domain.User;
import org.example.user.enums.Level;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EventUserLevelUpgradePolicy implements UserLevelUpgradePolicy{

    private IUserDao userDao;

    private MailSender mailSender;

    private static final int MIN_LOGIN_COUNT_FOR_SILVER = 30;
    private static final int MIN_RECOMMEND_FOR_GOLD = 20;
    private static final int MIN_RECOMMEND_FOR_PLATINUM = 100;

    public void setUserDao(IUserDao userDao){
        this.userDao = userDao;
    }

    public void setMailSender(MailSender mailSender){
        this.mailSender = mailSender;
    }

    public boolean canUpgradeLevel(User user){
        Level currentLevel = user.getLevel();

        switch(currentLevel){
            case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return (user.getRecommend() >= MIN_RECOMMEND_FOR_PLATINUM);
            case PLATINUM: return false;

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

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("tlatmsrud@naver.com");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() +" 로 업그레이드 되었습니다.");

        mailSender.send(mailMessage);
    }

}
