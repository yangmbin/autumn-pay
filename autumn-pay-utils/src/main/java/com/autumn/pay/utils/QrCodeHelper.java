package com.autumn.pay.utils;

import com.google.zxing.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.decoder.*;

public final class QrCodeHelper
{
	public static Bitmap Create(String content)
	{
		if (String.IsNullOrWhiteSpace(content))
		{
			throw new ArgumentNullException(nameof(content));
		}
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		ByteMatrix byteMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300);
		return byteMatrix.ToBitmap();
	}

	public static Bitmap Create(String content, String imagePath)
	{
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit typing in Java:
//		using (var image = Image.FromFile(imagePath))
		var image = Image.FromFile(imagePath);
		try
		{
			return Create(content, image);
		}
		finally
		{
			image.dispose();
		}
	}

	public static Bitmap Create(String content, Image centralImage)
	{
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		java.util.Hashtable hashtables = new java.util.Hashtable() { { EncodeHintType.CHARACTER_SET, "UTF-8" }, { EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H } };
		ByteMatrix byteMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 300, 300, hashtables);
		Bitmap bitmap = byteMatrix.ToBitmap();
		Image image = centralImage;
		Size encodeSize = multiFormatWriter.GetEncodeSize(content, BarcodeFormat.QR_CODE, 300, 300);
		int num = Math.min((int)((double)encodeSize.getWidth() / 3.5), image.getWidth());
		int num1 = Math.min((int)((double)encodeSize.getHeight() / 3.5), image.getHeight());
		int width = (bitmap.getWidth() - num) / 2;
		int height = (bitmap.getHeight() - num1) / 2;
		Bitmap bitmap1 = new Bitmap(bitmap.getWidth(), bitmap.getHeight(), PixelFormat.Format32bppArgb);
//C# TO JAVA CONVERTER NOTE: The following 'using' block is replaced by its Java equivalent:
//		using (Graphics graphic = Graphics.FromImage(bitmap1))
		Graphics graphic = Graphics.FromImage(bitmap1);
		try
		{
			graphic.InterpolationMode = InterpolationMode.HighQualityBicubic;
			graphic.SmoothingMode = SmoothingMode.HighQuality;
			graphic.CompositingQuality = CompositingQuality.HighQuality;
			graphic.DrawImage(bitmap, 0, 0);
		}
		finally
		{
			graphic.dispose();
		}
		Graphics graphic1 = Graphics.FromImage(bitmap1);
		graphic1.FillRectangle(Brushes.White, width, height, num, num1);
		graphic1.DrawImage(image, width, height, num, num1);
		return bitmap1;
	}
}