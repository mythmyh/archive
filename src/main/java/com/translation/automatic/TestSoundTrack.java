package com.translation.automatic;
import java.io.File;
import java.io.IOException;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;

public class TestSoundTrack {
	

	public  boolean check(File arg) {
       boolean canPlay=true;
		MP3File mp3File;
		
		try {
			mp3File = (MP3File) AudioFileIO.read(arg);
			MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();

			// 单位为秒
			 audioHeader.getTrackLength();
			
		} catch (CannotReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ReadOnlyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAudioFrameException e) {
			System.out.println("音频错误！");
			// TODO Auto-generated catch block
			canPlay=false;
		}
		return canPlay;
	}

}
