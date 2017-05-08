package org.mpcleague.common;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ImgurUploader implements Runnable {

    private File screenshot;

    public ImgurUploader(File screenshot) {

	this.screenshot = screenshot;

    }

    public File getScreenshotFile() {
	return screenshot;
    }

    public void run() {

	try {

	    Minecraft mc = Minecraft.getMinecraft();

	    JsonElement jelement = new JsonParser().parse(getImgurContent("6ebd41966b48249", getScreenshotFile()));
	    JsonObject jobject = jelement.getAsJsonObject();
	    jobject = jobject.getAsJsonObject("data");

	    StringSelection selection = new StringSelection(jobject.get("link").toString().replaceAll("\"", ""));
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);

	    IChatComponent msg = new ChatComponentText("Upload complete. Link copied to clipboard.");
	    mc.thePlayer.addChatMessage(msg);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public static String getImgurContent(String clientID, File image) throws IOException {
	URL url;
	url = new URL("https://api.imgur.com/3/image");
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	BufferedImage img = null;
	File file = image;
	
	System.out.println(file.getAbsolutePath());
	
	// read image
	img = ImageIO.read(file);
	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	ImageIO.write(img, "png", byteArray);
	byte[] byteImage = byteArray.toByteArray();
	String dataImage = Base64.getEncoder().encodeToString(byteImage);
	String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(dataImage, "UTF-8");

	conn.setDoOutput(true);
	conn.setDoInput(true);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Authorization", "Client-ID " + clientID);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

	conn.connect();
	StringBuilder stb = new StringBuilder();
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	wr.write(data);
	wr.flush();

	// Get the response
	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String line;
	while ((line = rd.readLine()) != null) {
	    stb.append(line).append("\n");
	}
	wr.close();
	rd.close();

	return stb.toString();
    }

}
