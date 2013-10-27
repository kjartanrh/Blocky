package is.ru.Blocky;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 23.10.2013
 * Time: 20:20
 * To change this template use File | Settings | File Templates.
 */
public class ChallengeParser {

    private static List<Challenge> challenges = new ArrayList<Challenge>();
    private enum CurrentXmlNode {
        NONE,
        CHALLENGE,
        PUZZLE,
        LEVEL,
        LENGTH,
        SETUP
    }

    public static List<Challenge> getChallenges(Activity activity)
            throws XmlPullParserException, IOException
    {
        CurrentXmlNode currentXmlNode = CurrentXmlNode.NONE;
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(R.xml.challenge_classic40);
        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.START_TAG)
            {
                String tag = xpp.getName();
                if( tag.equalsIgnoreCase("challenge")) {
                    currentXmlNode = CurrentXmlNode.CHALLENGE;
                } else if (tag.equalsIgnoreCase("puzzle")) {
                    currentXmlNode = CurrentXmlNode.PUZZLE;
                    int id = Integer.parseInt(xpp.getAttributeValue(null, "id"));
                    Challenge challenge = new Challenge(id);
                    challenges.add(challenge);
                } else if (tag.equalsIgnoreCase("level")) {
                    currentXmlNode = CurrentXmlNode.LEVEL;
                } else if (tag.equalsIgnoreCase("length")) {
                    currentXmlNode = CurrentXmlNode.LENGTH;
                } else if (tag.equalsIgnoreCase("setup")) {
                    currentXmlNode = CurrentXmlNode.SETUP;
                }
            }
            else if(eventType == XmlPullParser.TEXT)
            {
                String text = xpp.getText();
                switch( currentXmlNode) {
                    case CHALLENGE:
                        break;
                    case PUZZLE:
                        break;
                    case LEVEL:
                        challenges.get(challenges.size() - 1).setLevel(Integer.parseInt(text));
                        break;
                    case LENGTH:
                        challenges.get(challenges.size() - 1).setLength(Integer.parseInt(text));
                        break;
                    case SETUP:
                        parseSetup(text);
                        break;
                }
            }
            eventType = xpp.next();
        }
        xpp.close();
        return challenges;
    }

    private static void parseSetup(String setup) {

        if (setup != null) {
            String[] blocks = setup.replaceAll("\\s+","").split(",");
            if(challenges.size() > 0) {
                Challenge challenge = challenges.get(challenges.size() - 1);

                if(blocks.length > 0) {
                    for( int i = 0; i < blocks.length; i++ ) {
                        challenge.getBlocks().add(parseBlockPosition(blocks[i], (i == 0)));
                    }
                }
            }
        }
    }

    private static Challenge.BlockPosition parseBlockPosition(String blockPositionString, boolean escapee) {
        Challenge challenge = new Challenge();
        Challenge.BlockPosition blockPosition = challenge.new BlockPosition();
        String alignment = String.valueOf(blockPositionString.toCharArray()[1]);
        String x = String.valueOf(blockPositionString.toCharArray()[2]);
        String y = String.valueOf(blockPositionString.toCharArray()[3]);
        String blockLength = String.valueOf(blockPositionString.toCharArray()[4]);
        blockPosition.setEscapee(escapee);
        if(alignment.equalsIgnoreCase("H")) {
            blockPosition.setAlignment(Challenge.Alignment.Horizontal);
        } else {
            blockPosition.setAlignment(Challenge.Alignment.Vertical);
        }

        blockPosition.setX(Integer.parseInt(x));
        blockPosition.setY(Integer.parseInt(y));
        blockPosition.setBlockLength(Integer.parseInt(blockLength));

        return blockPosition;
    }
}
