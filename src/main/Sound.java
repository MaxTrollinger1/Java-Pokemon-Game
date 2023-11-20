package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[50];

    public Sound()
    {
        soundURL[0] = getClass().getResource("/sound/NBT_Music.wav");
        soundURL[1] = getClass().getResource("/sound/footsteps.wav");
        soundURL[2] = getClass().getResource("/sound/leaves.wav");
        soundURL[3] = getClass().getResource("/sound/UI_Click.wav");
        soundURL[4] = getClass().getResource("/sound/Success.wav");
        soundURL[5] = getClass().getResource("/sound/PokeCenter.wav");
        soundURL[6] = getClass().getResource("/sound/Battle.wav");
        soundURL[7] = getClass().getResource("/sound/Run.wav");
        soundURL[8] = getClass().getResource("/sound/battleWon.wav");
        soundURL[9] = getClass().getResource("/sound/attacks/Scratch.wav");
        soundURL[10] = getClass().getResource("/sound/attacks/Slam.wav");
        soundURL[11] = getClass().getResource("/sound/attacks/Cut.wav");
        soundURL[12] = getClass().getResource("/sound/attacks/Growl.wav");
        soundURL[13] = getClass().getResource("/sound/attacks/Headbutt.wav");
        soundURL[14] = getClass().getResource("/sound/attacks/Fang.wav");
        soundURL[15] = getClass().getResource("/sound/attacks/Pound.wav");
        soundURL[16] = getClass().getResource("/sound/attacks/Rage.wav");
        soundURL[17] = getClass().getResource("/sound/attacks/RazorWind.wav");
        soundURL[18] = getClass().getResource("/sound/attacks/Roar.wav");
        soundURL[19] = getClass().getResource("/sound/attacks/Tackle.wav");
        soundURL[20] = getClass().getResource("/sound/attacks/VineWhip.wav");
        soundURL[21] = getClass().getResource("/sound/attacks/WaterPulse.wav");
        soundURL[22] = getClass().getResource("/sound/attacks/Flamethrower.wav");

    }

    public void setFile(int index)
    {
        try
        {
            AudioInputStream as = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(as);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void play(float volume)
    {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));

        clip.start();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()
    {
        if(clip != null) clip.stop();
    }



}
