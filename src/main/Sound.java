package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[20];

    public Sound()
    {
        soundURL[0] = getClass().getResource("/sound/NBT_Music.wav");
        soundURL[1] = getClass().getResource("/sound/footsteps.wav");
        soundURL[2] = getClass().getResource("/sound/leaves.wav");
        soundURL[3] = getClass().getResource("/sound/UI_Click.wav");
        soundURL[4] = getClass().getResource("/sound/Success.wav");
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
        clip.stop();
    }



}
