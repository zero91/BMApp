package com.boostme.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;

import com.boostme.activity.R;
import com.boostme.bean.ConsultEntity;
import com.boostme.bean.QuestionEntity;

public class TestDatas {
	
	public static ArrayList<QuestionEntity> getCommDatas(int start, int num) {
		ArrayList<QuestionEntity> commuList = new ArrayList<QuestionEntity>();
		for (int i = start; i <= start + num; i++) {
			QuestionEntity e1 = new QuestionEntity();

			e1.setAuthor("张三" + i);
			e1.setTime(1423385484);
			e1.setTitle("求教!数据挖掘和机器学习有什么区别呢? 哪一种更难，或者两者是一样的东西？");
			e1.setFavourNum(i);
			e1.setReplyNum(i % 7);
			commuList.add(e1);
		}
		return commuList;
	}

	public static ArrayList<QuestionEntity> getCommDatas() {
		ArrayList<QuestionEntity> commuList = new ArrayList<QuestionEntity>();
		QuestionEntity e1 = new QuestionEntity();
		QuestionEntity e2 = new QuestionEntity();

		QuestionEntity e3 = new QuestionEntity();
		QuestionEntity e4 = new QuestionEntity();

		e1.setAuthor("张山");
		e1.setTime(1423385484);
		e1.setTitle("我叫你一声sun子, ssssssggg??ssssss吗！哈哈哈ssss哈哈哈哈哈哈哈哈哈哈哈哈哈....");
		e1.setFavourNum(10);
		e1.setReplyNum(10);

		e2.setAuthor("李斯");
		e2.setTime(1423385484);
		e2.setTitle("我叫你一声小niang子,答应我吧！哈哈哈ffff哈哈哈哈哈哈哈哈哈哈哈哈哈....");
		e2.setFavourNum(10);
		e2.setReplyNum(10);

		e3.setAuthor("王武");
		e3.setTime(1423385484);
		e3.setTitle("我叫你一声er子,你敢答应吗！！！！！！！！！哈哈哈哈哈eeeee哈哈哈哈哈哈哈哈....");
		e3.setFavourNum(10);
		e3.setReplyNum(10);

		e4.setAuthor("马留");
		e4.setTime(1423385484);
		e4.setTitle("我叫你一声大sun子,你敢答应吗！！！！！！！哈哈哈哈哈哈哈....");
		e4.setFavourNum(10);
		e4.setReplyNum(10);

		commuList.add(e1);
		commuList.add(e2);
		commuList.add(e3);
		commuList.add(e4);

		return commuList;
	}

	public static ArrayList<ConsultEntity> getConsultDatas(Activity activity) {
		ArrayList<ConsultEntity> consultList = new ArrayList<ConsultEntity>();
		ConsultEntity e1 = new ConsultEntity();
		ConsultEntity e2 = new ConsultEntity();
		ConsultEntity e3 = new ConsultEntity();
		ConsultEntity e4 = new ConsultEntity();
		
		e1.setPublisherName("雅蠛蝶");
		e1.setDescription("雅蠛蝶（やめて），由于近似日语发音“呀买太”，所以也被广大网友广泛使用。日语原意：“别”、“不要”。类似语言还有：“呀咩爹”。\n亚美蝶，雅蠛蝶，呀买碟，亚麻跌，呀卖呆，雅咩蝶，亚麻得，并不是这些YaMeTe的名称");
//		e1.setServiceCategoty("Tokyo Hot University");
		e1.setPrice("￥3000");
		
		e2.setPublisherName("泰坦蟒");
		e2.setDescription("最近一期《自然》杂志发表了Jason J. Head等人的论文《新热带界古新统发现巨型蟒蛇暗示过去赤道更热》，文中称在南美洲哥伦比亚东北部的瓜希拉半岛大约6000万年前到5800万年前的塞雷洪组（Cerrejon Formation）地层发现了一条巨大的蛇类化石。命名为塞雷洪泰坦蟒（Titanoboacerrejonensis），属名的意思是“泰坦的蟒蛇”，种名是纪念发现地塞雷洪。");
//		e2.setServiceCategoty("South Africa Hot University");
		e2.setPrice("$3000");
		
		e3.setPublisherName("恐狼");
		e3.setDescription("恐狼是犬属已灭绝的一个物种，在更新世（距今约260万年至1万年）的北美洲非常普遍。虽然它与灰狼有关，但却不是任何现存物种的直系祖先。恐狼与灰狼在北美洲一同生存了约十万年。");
//		e3.setServiceCategoty("America Hot University");
		e3.setPrice("$3000");
		
		e4.setPublisherName("沧龙");
		e4.setDescription("沧龙（Mosasaurus）是中生代海洋中最大的顶级掠食者。虽然它的历史很短（从陆地上的蜥蜴进化而来，在白垩纪中晚期才出现并且迅速繁衍，随后和恐龙一起灭绝），但却一路平步青云、把比它历史早远得多的海洋爬行动物[1] 鱼龙目、蛇颈龙目、上龙亚目赶尽杀绝。");
//		e4.setServiceCategoty("Peking Hot University");
		e4.setPrice("￥5000");
		
		consultList.add(e1);
		consultList.add(e2);
		consultList.add(e3);
		consultList.add(e4);
		
		
		return consultList;
	}
	
	public static String getUriString(int i, Activity activity){
		Resources r = activity.getResources();
		 Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
		    + r.getResourcePackageName(i) + "/"
		    + r.getResourceTypeName(i) + "/"
		    + r.getResourceEntryName(i));
		 
		 System.out.println(r.getString(R.drawable.konglang));
		 Uri u = Uri.parse(r.getString(R.drawable.konglang));
		 
		 System.out.println(u);
		 
		 return uri.getPath();
	}

}
