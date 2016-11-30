package com.mobagym.Utils;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobagym.mobagym.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by behzad on 29/11/16.
 */

public class HtmlHandler
{
    Context context;
    String html;
    public ArrayList<String> parts = new ArrayList<>();
    public ArrayList<String> srcs = new ArrayList<>();
    public ArrayList<String> alts = new ArrayList<>();
    public ArrayList<TextView> partsViews = new ArrayList<>();
    public ArrayList<ImageView> imageViews = new ArrayList<>();
    public HtmlHandler(Context context, String html)
    {
        this.context = context;
        this.html = html;

        Document document = Jsoup.parse(html);
        Elements elements = document.select("img");
        Log.e("HTML","e-count"+String.valueOf(elements.size()));
        for(int i = 0 ; i < elements.size() ; i++)
        {
            Log.e("HTML","SRC="+elements.get(i).attr("src"));
            Log.e("HTML","ALT="+elements.get(i).attr("alt"));
            srcs.add(elements.get(i).attr("src"));
            alts.add(elements.get(i).attr("alt"));
        }
        while(html.contains("<img"))
        {
            String img = html.substring(html.indexOf("<img"),html.indexOf("/>"))+"/>";
            String part = html.substring(0, html.indexOf("<img"));
            if (part.endsWith("<p>"))
                part = part.substring(0, part.lastIndexOf("<p>"));
            if (part.startsWith("</p>"))
                part = part.replaceFirst("</p>", "");
            parts.add(part);
            html  = html.replace(part,"").replaceFirst(img,"");
            Log.e("HTML","Part :"+part);
            Log.e("HTML","IMG :"+img);
            Log.e("HTML","NEW HTML :"+html);
        }

        Log.e("HTML","GG");
    }
    public void MakeViews(LinearLayout ll,int textViewLayoutId,int imageViewLayoutId)
    {

    }
    public void MakeViews(LinearLayout ll)
    {
        for(int i = 0 ; i < parts.size() ; i++)
        {
            TextView textView = new TextView(context);
            textView.setText(Html.fromHtml(parts.get(i)));
            ll.addView(textView);
            partsViews.add(textView);
            ImageView imageView = new ImageView(context);
            //imageView.setImageDrawable(result);
            Picasso.with(context).load(srcs.get(i))
                    .placeholder(R.drawable.calendar)
                    .error(R.drawable.visible)
                    .into(imageView);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ll.addView(imageView);
            imageViews.add(imageView);
        }
    }
}
