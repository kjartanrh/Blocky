package is.ru.Blocky;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 22.10.2013
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity {

    private List<Challenge> challenges = new ArrayList<Challenge>();;
    private enum CurrentXmlNode {
        NONE,
        CHALLENGE,
        PUZZLE,
        LEVEL,
        LENGTH,
        SETUP
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        TextView myXmlContent = (TextView)findViewById(R.id.gameboard);
        String stringXmlContent;
        try {
            stringXmlContent = getEventsFromAnXML(this);
            myXmlContent.setText(stringXmlContent);
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String getEventsFromAnXML(Activity activity)
            throws XmlPullParserException, IOException
    {
        CurrentXmlNode currentXmlNode = CurrentXmlNode.NONE;
        StringBuffer stringBuffer = new StringBuffer();
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(R.xml.challenge_classic40);
        xpp.next();
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.START_DOCUMENT)
            {
                stringBuffer.append("--- Start XML ---");
            }
            else if(eventType == XmlPullParser.START_TAG)
            {
                String tag = xpp.getName();
                stringBuffer.append("\nSTART_TAG: " + tag);
                if( tag.equalsIgnoreCase("challenge")) {
                    currentXmlNode = CurrentXmlNode.CHALLENGE;
                } else if (tag.equalsIgnoreCase("puzzle")) {
                    currentXmlNode = CurrentXmlNode.PUZZLE;
                    Challenge challenge = new Challenge();
                    challenges.add(challenge);
                } else if (tag.equalsIgnoreCase("level")) {
                    currentXmlNode = CurrentXmlNode.LEVEL;
                } else if (tag.equalsIgnoreCase("length")) {
                    currentXmlNode = CurrentXmlNode.LENGTH;
                } else if (tag.equalsIgnoreCase("setup")) {
                    currentXmlNode = CurrentXmlNode.SETUP;
                }
            }
            else if(eventType == XmlPullParser.END_TAG)
            {
                stringBuffer.append("\nEND_TAG: "+xpp.getName());
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
                stringBuffer.append("\nTEXT: " + text);
            }
            eventType = xpp.next();
        }
        stringBuffer.append("\n--- End XML ---");
        xpp.close();
        return stringBuffer.toString();

    }

    private void parseSetup(String setup) {

        if (setup != null) {
            String[] blocks = setup.replaceAll("\\s+","").split(",");
            if(challenges.size() > 0) {
                Challenge challenge = challenges.get(challenges.size() - 1);

                if(blocks.length > 0) {
                    challenge.setEscapee(parseBlockPosition(blocks[0]));

                    if(blocks.length > 1) {
                        for( int i = 1; i < blocks.length; i++ ) {
                            challenge.getObsticles().add(parseBlockPosition(blocks[i]));
                        }
                    }
                }
            }
        }
    }

    public Challenge.BlockPosition parseBlockPosition(String blockPositionString) {
        Challenge challenge = new Challenge();
        Challenge.BlockPosition blockPosition = challenge.new BlockPosition();
        Log.d("parseBlockPosition: ", blockPositionString);
//        String alignment = blockPositionString.substring(1,1);
//        String x = blockPositionString.substring(2,1);
//        String y = blockPositionString.substring(3,1);
//        String blockLength = blockPositionString.substring(4,1);
//        if(blockPositionString.substring(1,1).equalsIgnoreCase("H")) {
//            blockPosition.setAlignment(Challenge.Alignment.Horizontal);
//        } else {
//            blockPosition.setAlignment(Challenge.Alignment.Vertical);
//        }
//
//        blockPosition.setX(Integer.parseInt(blockPositionString.substring(2,1)));
//        blockPosition.setY(Integer.parseInt(blockPositionString.substring(3,1)));
//        blockPosition.setBlockLength(Integer.parseInt(blockPositionString.substring(4,1)));

        return blockPosition;
    }
}
