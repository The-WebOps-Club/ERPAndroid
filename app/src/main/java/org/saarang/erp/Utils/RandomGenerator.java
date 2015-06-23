package org.saarang.erp.Utils;

import org.saarang.erp.Objects.ERPPost;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ahammad on 21/06/15.
 */
public class RandomGenerator {

    public static ArrayList<ERPPost> getRandomPosts(int count){
        /**
         * Generating random data set
         */
        ArrayList<ERPPost> list = new ArrayList<>();
        for (int i= 1; i<count; i++){
            int random = new Random().nextInt(11);
            ERPPost post = new ERPPost();
            post.setInfo(infos[random]);
            post.setPostedBy(createdBy[random]);
            post.setTitle(titles[random]);
            post.setProfilePic(profilePics[random]);
            post.setWall(walls[random]);
            post.setAction(action[random]);
            list.add(post);
        }
        return list;
    }

    public static String[] profilePics = {
            "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xap1/v/t1.0-9/10917107_861605683904660_2916721572559378705_n.jpg?oh=f7614b6d5ec547ea1daaa7f9deb3bb90&oe=56213825&__gda__=1446170873_73d74c10d3ce73374cea50fdb49b1ff0",
            "https://scontent-bru2-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/11203007_921922381183913_4028899080210101313_n.jpg?oh=3434ba5601d45ab64ba79fa5b47e62d2&oe=55EA7175",
            "https://fbcdn-sphotos-b-a.akamaihd.net/hphotos-ak-xap1/v/t1.0-9/11150736_841536632601763_3571320871133991232_n.jpg?oh=e2519ad83d8565ec4309bc86ac34c46b&oe=563282BE&__gda__=1442128482_225a00cd1b9eee758887b357e59e2787",
            "https://scontent-bru2-1.xx.fbcdn.net/hphotos-xpf1/v/t1.0-9/11037893_10153183771293120_5412153897018827952_n.jpg?oh=554589223bc2e0fb17504e4339a50a10&oe=5631CC21",
            "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-prn2/t31.0-8/1404853_611497292226210_187597109_o.jpg",
            "https://scontent-bru2-1.xx.fbcdn.net/hphotos-xaf1/v/t1.0-9/10868162_749052845174509_560164992827574628_n.jpg?oh=424fa965322d337eb2b870aa8a369ae1&oe=56345C9B",
            "https://scontent-bru2-1.xx.fbcdn.net/hphotos-xpf1/v/t1.0-9/10628423_955026451185930_7162397556378586920_n.jpg?oh=b514e17a074dd1f335a1f20e148373a6&oe=55E73530",
            "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-xap1/v/t1.0-9/10917107_861605683904660_2916721572559378705_n.jpg?oh=f7614b6d5ec547ea1daaa7f9deb3bb90&oe=56213825&__gda__=1446170873_73d74c10d3ce73374cea50fdb49b1ff0",
            "https://scontent-bru2-1.xx.fbcdn.net/hphotos-xta1/v/t1.0-9/11203007_921922381183913_4028899080210101313_n.jpg?oh=3434ba5601d45ab64ba79fa5b47e62d2&oe=55EA7175",
            "https://scontent-bru2-1.xx.fbcdn.net/hphotos-xpf1/v/t1.0-9/11037893_10153183771293120_5412153897018827952_n.jpg?oh=554589223bc2e0fb17504e4339a50a10&oe=5631CC21",
            "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-prn2/t31.0-8/1404853_611497292226210_187597109_o.jpg"
    };

    public static String[] createdBy ={
            "Msvs Prasad",
            "Supreet Hegde",
            "Sai Kiran Vaddi",
            "John Abraham",
            "Shahidh K Muhammed",
            "Varun Teja Salady ",
            "Aqel Ahammad K P",
            "Msvs Prasad",
            "Supreet Hegde",
            "John Abraham",
            "Shahidh K Muhammed",

    };

    public static String[] infos = {
            "Those who bought Saarang T-Shirts on or before 7th January 2015, comment here   your saarang dept and roll number.",
            "Dear all,\n" +
                    "\n" +
                    "\n" +
                    "All Saarang 2016 Core\n" +
                    "aspirants are requested to start meeting the incumbent Cultural Affairs\n" +
                    "Secretaries, the newly elected Cultural Affairs Secretaries, the Core Members of Saarang 2015 and the Cultural Club Conveners of 2014-15.   We will be officially releasing the Core applications through Smail shortly.\n" +
                    "\n" +
                    "Selections to the positions of CORE members in the Saarang\n" +
                    "Organizing Committee are made by a committee having the Dean (Students)\n" +
                    "as the Chairperson, the Advisor (Cultural) as the Convenor, the \n" +
                    "incumbent Cultural Affairs Secretaries, the Core Members of Saarang 2015 and the newly elected Cultural Affairs Secretaries. Any changes to the current procedure will be informed in due course.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Please note that the entire process involves the office of the Dean (Students), Advisor (Cultural) and the Speaker (SAC). \n" +
                    "\n" +
                    "All the aspiring Coordinators are also requested to start meeting the concerned Core and Coordinators. We will be having an ACM soon after the Core selection.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Salady & Supreet",
            "Dear all,\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "I hope you all have got enogh rest by bunking all classes \n" +
                    "\n" +
                    "On a serious note, the entire Core Team, Saarang 2015 is very happy with all your efforts without which we wouldn't have had the best Saarang that we have ever seen. We request you to take forward the culture that we have started and make a better Saarang in the coming years. Please try to be a part of Saarang in coming years of your insti life, your experience is valuable. I have lot more to tell you people, it's better I do that meeting each team personally. Until then please try to complete some of the left out work if you have any, so that we can close our accounts for this year.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Also, we will treat you guys soon. Stay tuned for the updates",
            "All saarang coordinators who bought EDM gallery tickets will be issued \n" +
                    "gallery tickets and the upgrading will be done at special gate and sent \n" +
                    "to bowl.ID card and coord badge are mandatory.",
            "We have released our android app in google play store http://saarang.org/android\n" +
                    "\n" +
                    "Please download and let us know the feedback.",
            "Guys,\n" +
                    "\n" +
                    "Great work so far. With just under a week to go for Saarang it's high time that we help Saarang reach out to a large number of people and turn all our efforts into a huge success.\n" +
                    "\n" +
                    "Follow the link to render support to the Saarang Thunderclap   here and support it with FaceBook.\n" +
                    "\n" +
                    "This thunderclap will work only if we get 250+ supporters in the next five days.\n" +
                    "\n" +
                    "Support is appreciated.",
            "None will be reimbursed if materials or printing requirements are met from their own pocket. Material and printing requirements should go through  Facilities team.  Facilities   team can procure them only using vouchers.",
            "The Saarang Online Photography Contest goes live   today at 7 pm. The previous editions of the competition have seen huge participation from inside and outside campus folks. We hope to replicate the same this year as well. To achieve that we require all of you to support the campign by liking, sharing and even participating in OPC.\u200B",
            "The deadline for submitting bills related to the purchase   made before 15/11/2014 is closed. The bills dated on or before 15/11/2014 won't be accepted from now onwards. These will be reimbursed from 26th November. Collect the money from your cores or finance managers(coordinators) allotted for your departments.",
            "Finance team will religiously follow the Finance Policy, Saarang:   https://docs.google.com/document/d/1oJrk…\n" +
                    "\n" +
                    "Find coordinators allotted to look after your departments at: https://docs.google.com/spreadsheets/d/1…\n" +
                    "\n" +
                    "Kindly adhere to the policy.",
            "Sorry for all the inconvenieces. Our website is back online."
    };

    public static final String[] titles = {
            "T-shirts",
            "Call for Aspiring Cores and Coordinators || Saarang 2016",
            "Thank You Saarang Family",
            "Free tickets!!!!",
            "Android app, Saarang 2015",
            "Get ready for Saarang 2015",
            "Last date for settling bill",
            "Saarang Photography",
            "Deadlines ",
            "Finance policy",
            "Website is back online"
    };

    public static final String[] walls = {
            "Finance",
            "Cores",
            "Saarang 2015",
            "Saarang 2015",
            "Web and Mobile Operations",
            "Finance",
            "Finance",
            "Design and Media",
            "Marketing and Sales",
            "Finance",
            "Web and Mobile Operations"
    };

    public static  final String[] action={
            "Posted",
            "Commented",
            "Commented",
            "Posted",
            "Posted",
            "Commented",
            "Commented",
            "Posted",
            "Posted",
            "Commented",
            "Commented",

    };



}
