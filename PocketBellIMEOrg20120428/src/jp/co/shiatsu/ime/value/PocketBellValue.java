package jp.co.shiatsu.ime.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.R;

public class PocketBellValue {

	private PocketBellValue() {
	}
	
	public static final HashMap<String,String> codeAry = new HashMap<String,String>() {{
		put("11","あ");put("12","い");put("13","う");put("14","え");put("15","お");put("16","a");put("17","b");put("18","c");put("19","d");put("10","e");
		put("21","か");put("22","き");put("23","く");put("24","け");put("25","こ");put("26","f");put("27","g");put("28","h");put("29","i");put("20","j");
		put("31","さ");put("32","し");put("33","す");put("34","せ");put("35","そ");put("36","k");put("37","l");put("38","m");put("39","n");put("30","o");
		put("41","た");put("42","ち");put("43","つ");put("44","て");put("45","と");put("46","p");put("47","q");put("48","r");put("49","s");put("40","t");
		put("51","な");put("52","に");put("53","ぬ");put("54","ね");put("55","の");put("56","u");put("57","v");put("58","w");put("59","x");put("50","y");
		put("61","は");put("62","ひ");put("63","ふ");put("64","へ");put("65","ほ");put("66","z");put("67","？");put("68","！");put("69","ー");put("60","/");
		put("71","ま");put("72","み");put("73","む");put("74","め");put("75","も");put("76","¥");put("77","&");put("78","*");put("79","#");put("70","♪");
		put("81","や");put("82","(");put("83","ゆ");put("84",")");put("85","よ");put("86","、");put("87","。");put("88"," ");put("89","♥");put("80","");
		put("91","ら");put("92","り");put("93","る");put("94","れ");put("95","ろ");put("96","1");put("97","2");put("98","3");put("99","4");put("90","5");
		put("01","わ");put("02","を");put("03","ん");put("04","\"");put("05","'");put("06","6");put("07","7");put("08","8");put("09","9");put("00","0");
		put("2104","が");	put("2204","ぎ");	put("2304","ぐ");	put("2404","げ");	put("2504","ご");
		put("3104","ざ");	put("3204","じ");	put("3304","ず");	put("3404","ぜ");	put("3504","ぞ");
		put("4104","だ");	put("4204","ぢ");	put("4304","づ");	put("4404","で");	put("4504","ど");
		put("6104","ば");	put("6204","び");	put("6304","ぶ");	put("6404","べ");	put("6504","ぼ");
		put("6105","ぱ");	put("6205","ぴ");	put("6305","ぷ");	put("6405","ぺ");	put("6505","ぽ");	
		put("1111","ぁ");put("1112","ぃ");put("1113","ぅ");put("1114","ぇ");put("1115","ぉ");put("1116","A");put("1117","B");put("1118","C");
		put("1119","D");put("1120","E");
		put("1216","F");put("1217","G");put("1218","H");put("1219","I");put("1220","J");
		put("1316","K");put("1317","L");put("1318","M");put("1319","N");put("1320","O");
		put("1413","っ");put("1416","P");put("1417","Q");put("1418","R");put("1419","S");put("1420","T");
		put("1516","U");put("1517","V");put("1518","W");put("1519","X");put("1520","Y");
		put("1616","Z");put("1617","?");put("1618","!");put("1619","-");put("1620","/");
		put("1716","¥");put("1717","&");put("1718","*");put("1719","#");put("1720","♪");
		put("1811","ゃ");put("1813","ゅ");put("1815","ょ");put("1816",",");put("1817",".");put("1818"," ");put("1819","♥");
		put("1916","1");put("1917","2");put("1918","3");put("1919","4");put("1920","5");
		put("2016","6");put("2017","7");put("2018","8");put("2019","9");put("2020","0");
		put("2021","@");put("2022","-");put("2023","_");put("2024",":");put("2025","~");put("2026",";");
		
	}};
	public static final HashMap<String,String> codeAnotherAry = new HashMap<String,String>() {{
		
		put("11","ぁ");put("12","ぃ");put("13","ぅ");put("14","ぇ");put("15","ぉ");put("16","A");put("17","B");put("18","C");put("19","D");put("10","E");
		put("26","F");put("27","G");put("28","H");put("29","I");put("20","J");
		put("36","K");put("37","L");put("38","M");put("39","N");put("30","O");
		put("43","っ");put("46","P");put("47","Q");put("48","R");put("49","S");put("40","T");
		put("56","U");put("57","V");put("58","W");put("59","X");put("50","Y");
		put("66","Z");put("67","?");put("68","!");put("69","-");put("60","/");
		put("76","¥");put("77","&");put("78","*");put("79","#");put("70","♪");
		put("81","ゃ");put("83","ゅ");put("85","ょ");put("86",",");put("87",".");put("88"," ");put("89","♥");
		put("96","1");put("97","2");put("98","3");put("99","4");put("90","5");
		put("01","ゎ");put("06","6");put("07","7");put("08","8");put("09","9");put("00","0");
		
	}};
	
	//濁点の可能性がある配列を定義
	public static List<String> dakutenList = new ArrayList<String>(){{
		add("か");add("き");add("く");add("け");add("こ");
		add("さ");add("し");add("す");add("せ");add("そ");
		add("た");add("ち");add("つ");add("て");add("と");
		add("は");add("ひ");add("ふ");add("へ");add("ほ");
	}};
	
	public static final HashMap<String,String> code2Ary = new HashMap<String,String>() {{

		put("11","ぁ");put("12","ぃ");put("13","ぅ");put("14","ぇ");put("15","ぉ");put("16","A");put("17","B");put("18","C");put("19","D");put("10","E");
		put("26","F");put("27","G");put("28","H");put("29","I");put("20","J");
		put("36","K");put("37","L");put("38","M");put("39","N");put("30","O");
		put("43","っ");put("46","P");put("47","Q");put("48","R");put("49","S");put("40","T");
		put("56","U");put("57","V");put("58","W");put("59","X");put("50","Y");
		put("66","Z");put("67","?");put("68","!");put("69","-");put("60","/");
		put("76","¥");put("77","&");put("78","*");put("79","#");put("70","♪");
		put("81","ゃ");put("83","ゅ");put("85","ょ");put("86",",");put("87",".");put("88"," ");put("89","♥");
		put("96","1");put("97","2");put("98","3");put("99","4");put("90","5");
		put("06","6");put("07","7");put("08","8");put("09","9");put("00","0");
		
	}};
	public static final HashMap<String,String> kanaAry = new HashMap<String,String>() {{
		put("a","あ");put("i","い");put("u","う");put("e","え");put("o","お");
		put("ka","か");put("ki","き");put("ku","く");put("ke","け");put("ko","こ");
		put("qa","くぁ");put("qi","き");put("qu","く");put("qe","くぇ");put("qo","くぉ");
		put("sa","さ");put("si","し");put("shi","し");put("su","す");put("se","せ");put("so","そ");
		put("ta","た");put("ti","ち");put("tu","つ");put("te","て");put("to","と");
		put("na","な");put("ni","に");put("nu","ぬ");put("ne","ね");put("no","の");
		put("ha","は");put("hi","ひ");put("hu","ふ");put("he","へ");put("ho","ほ");
		put("ma","ま");put("mi","み");put("mu","む");put("me","め");put("mo","も");
		put("ya","や");put("yu","ゆ");put("yo","よ");
		put("ra","ら");put("ri","り");put("ru","る");put("re","れ");put("ro","ろ");
		put("wa","わ");put("wo","を");put("nn","ん");
		put("ga","が");put("gi","ぎ");put("gu","ぐ");put("ge","げ");put("go","ご");
		put("za","ざ");put("zi","じ");put("zu","ず");put("ze","ぜ");put("zo","ぞ");
		put("ja","じゃ");put("ji","じ");put("ju","じゅ");put("je","じぇ");put("jo","じょ");
		put("da","だ");put("di","ぢ");put("du","づ");put("de","で");put("do","ど");
		put("ba","ば");put("bi","び");put("bu","ぶ");put("be","べ");put("bo","ぼ");
		put("kya","きゃ");put("kyu","きゅ");put("kyo","きょ");
		put("gya","ぎゃ");put("gyu","ぎゅ");put("gyo","ぎょ");
		put("fa","ふぁ");put("fi","ふぃ");put("fu","ふ");put("fe","ふぇ");put("fo","ふぉ");
		put("sha","しゃ");put("shu","しゅ");put("sho","しょ");
		put("sya","しゃ");put("syu","しゅ");put("syo","しょ");
		put("hya","ひゃ");put("hyu","ひゅ");put("hyo","ひょ");
		put("mya","みゃ");put("myu","みゅ");put("myo","みょ");
		put("jya","じゃ");put("jyu","じょ");put("jyo","じょ");
		
	}};
	
	public static final HashMap<String,String> katakanaAry = new HashMap<String,String>() {{
		put("あ","ア");put("い","イ");put("う","ウ");put("え","エ");put("お","オ");
		put("か","カ");put("き","キ");put("く","ク");put("け","ケ");put("こ","コ");
		
		put("さ","サ");put("し","シ");put("す","ス");put("せ","セ");put("そ","ソ");
		put("た","タ");put("ち","チ");put("つ","ツ");put("て","テ");put("と","ト");
		put("な","ナ");put("に","ニ");put("ぬ","ヌ");put("ね","ネ");put("の","ノ");
		put("は","ハ");put("ひ","ヒ");put("ふ","フ");put("へ","ヘ");put("ほ","ホ");
		put("ま","マ");put("み","ミ");put("む","ム");put("め","メ");put("も","モ");
		put("や","ヤ");put("ゆ","ユ");put("よ","ヨ");
		put("ら","ラ");put("り","リ");put("る","ル");put("れ","レ");put("ろ","ロ");
		put("わ","ワ");put("を","ヲ");put("ん","ン");
		put("が","ガ");put("ぎ","ギ");put("ぐ","グ");put("げ","ゲ");put("ご","ゴ");
		put("ざ","ザ");put("じ","ジ");put("ず","ズ");put("ぜ","ゼ");put("ぞ","ゾ");
		put("じゃ","ジャ");put("じゅ","ジュ");put("じぇ","ジェ");put("じょ","ジョ");
		put("だ","ダ");put("ぢ","ヂ");put("づ","ヅ");put("で","デ");put("ど","ド");
		put("ば","バ");put("び","ビ");put("ぶ","ブ");put("べ","ベ");put("ぼ","ボ");
		put("きゃ","ギャ");put("きゅ","キュ");put("きょ","キョ");
		put("ぎゃ","ギャ");put("ぎゅ","ギュ");put("ぎょ","ギョ");
		put("ふぁ","ファ");put("ふぃ","フィ");put("ふぇ","フェ");put("ふぉ","フォ");
		put("しゃ","シャ");put("しゅ","シュ");put("しょ","ショ");
		
		put("ひゃ","ヒャ");put("ひゅ","ヒュ");put("ひょ","ヒョ");
		put("みゃ","ミャ");put("みゅ","ミュ");put("みょ","ミョ");
		
		
	}};
	
	public static HashMap<String,String> upperInfo = new HashMap<String,String>() {{
		put("ぁ","あ");put("ぃ","い");put("ぅ","う");put("ぇ","え");put("ぉ","お");
		put("ゃ","や");put("ょ","よ");put("ゅ","ゆ");put("っ","づ");
	}};
	public static HashMap<String,String> lowerInfo = new HashMap<String,String>() {{
		put("あ","ぁ");put("い","ぃ");put("う","ぅ");put("え","ぇ");put("お","ぉ");
		put("や","ゃ");put("よ","ょ");put("ゆ","ゅ");put("づ","っ");
	}};
	
	public static final List<String> kaomojiList = new ArrayList<String>() {{
		add("+（0ﾟ・∀・） +");add("ヽ(ﾟ∀ﾟ)メ(ﾟ∀ﾟ)メ(ﾟ∀ﾟ)ノ");add("┐(´д｀)┌");
		add("(´・д・｀)");add("(´・д・｀)");add("(´・д・｀)");add("(´∀｀)");
		add("(´Д⊂");add("(''A`)");add("(・∀・ )っ/凵⌒☆");add("ヽ(´ー｀)ノ");
		add("(ﾟдﾟ)");add("( ﾟдﾟ)");add("(　ﾟдﾟ)");add("( ´∀｀)σ)Д`)");
		add("m9(^Д^)");add("(´；ω；｀)");add("⊂二二二（　＾ω＾）二⊃");
		add("( ´,_ゝ｀)");add("｀;:ﾞ;｀;･(ﾟεﾟ )");add("(　´_ゝ｀)");
		add("(　´д)ﾋｿ(´д｀)ﾋｿ(д｀ )");add("(ｌｌｌﾟДﾟ)");add("( ´Д｀)ﾉ~");
		add("(;´Д｀)");add("(;ﾟ∀ﾟ)=3");add("(ﾟДﾟ)");add("(￣ー￣)");
		add("(・∀・)");add("<丶｀∀´>");add("／(^o^)＼");
		add("＼(^o^)／");add("(； ･`д･´)");add("｡･ﾟ･(ﾉД｀)･ﾟ･｡");
		add("(´Д⊂ヽ");add("( ´∀｀)人(´∀｀ )");add("ヽ(ﾟ∀ﾟ)ﾉ");add("(*´∀｀)");
		add("（；^ω^）");add("( ﾟДﾟ)y─┛~~");add(";y=ｰ( ﾟдﾟ)･∵.");
		add("(´・ω・｀)");add("|彡ｻｯ");add("(ﾟДﾟ)");add("( TДT)");
		add("|ﾟДﾟ)))");add("ｷﾀｷﾀｷﾀｷﾀ━━━(ﾟ∀ﾟ≡(ﾟ∀ﾟ≡ﾟ∀ﾟ)≡ﾟ∀ﾟ)━━━━!!");
		add("ｷﾀ━━━ヽ(∀ﾟ )人(ﾟ∀ﾟ)人( ﾟ∀)ノ━━━!!");
		add("ｷﾀ━━━━(ﾟ∀ﾟ)━━━━!!");
		add("(ﾟεﾟ)");add("(　´Д｀)");add("( ﾟДﾟ)･∵.");
		add("((((；ﾟДﾟ))))");add("ｶﾞ━━(;ﾟДﾟ)━━ﾝ!!");
		add("|дﾟ)");add("(´・ω・)");add("щ(ﾟдﾟщ)");add("φ(｀д´)");
		add("(・∀・)");add("(つ∀-)");add("(　´∀｀)");add("( ノﾟДﾟ)");
		add("( ノﾟДﾟ)");add("(・∀・)つ目");add("(　ﾟ∀ﾟ)o彡");
		add("工ｴｴｪｪ(´д｀)ｪｪｴｴ工");add("ヽ( ・∀・)ﾉ");add("ヽ(`Д´)ﾉ");
		add("Σ(ﾟДﾟ)");add("(ﾟ⊿ﾟ)");add("ι(´Д｀υ)");add("(ﾉ∀`)");
		add("(=ﾟωﾟ)ﾉ");add("(ﾟ∀ﾟ)");add("(ﾟДﾟ )");add("( ﾟДﾟ)");
		add("(・∀・)");add("(；´Д｀)");add("（；^ω^）");add("(・∀・)");
		add("(・A・)");add("(ﾟдﾟ)");
	}};
	public static final List<String> kigouList = new ArrayList<String>() {{
		add("。");add("？");add("！");add("ー");
		add("、");add("!");add("?");add("'");add("#");add("&");add("%");add("(");add(")");
		add("*");add("¥");add("+");add("[");add("]");add("{");add("}");add("@");
		add("<");add(">");add("-");add("_");add("/");add(",");add(".");
	}};
	public static final List<String> defaultEndList = new ArrayList<String>() {{
		add("。");add("？");add("！");add("、");
		add("!");add("?");add(",");add(".");add("の");add("は");add("に");add("が");
		add("を");add("です");add("と");add("で");add("も");add("って");add("か");
		add("っての");add("だ");add("ね");add("よ");add("から");add("だけど");
		add("ですか");add("を");add("でしょ");add("でしょう");add("でも");
		add("だった");add("でした");add("でしょうか");add("ですが");add("かな");
		add("じゃ");add("には");add("まで");add("よね");add("じゃない");add("だよ");
		add("なんだ");add("がある");add("を");add("ですね");add("では");add("みたい");
		add("くらい");add("だから");add("らしい");add("だけ");add("かも");add("だよね");
		add("なので");add("なら");add("なんだけど");add("だろう");add("ですよ");
		add("とか");add("のこと");add("ですけど");add("なの");add("にも");
		add("だろ");add("だったら");add("ってこと");add("なんて");add("について");
		add("ねぇ");add("があった");add("があり");add("がない");add("ぐらい");
		add("しか");add("じゃなく");add("だって");add("だね");add("なんか");
		add("のところ");add("はない");add("じゃないか");add("じゃん");
		add("だし");add("ではない");add("として");add("なのか");add("なんです");
		add("にでも");add("へ");add("みたいだ");add("もある");add("があったら");
		add("があるので");add("じゃなくて");add("だけどね");add("だったの");
		add("だったんだ");add("での");add("なのに");add("にしても");add("によって");
	}};
	public static final List<String> emojiStringList = new ArrayList<String>() {{
//		add(new String(new byte[]{-13,-66,-116,-72}, 0, 4));
//		add(new String(new byte[]{-13,-66,-115,-121}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-89}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-66}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-88}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-87}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-90}, 0, 4));
//		add(new String(new byte[]{-13,-66,-115,-124}, 0, 4));
//		add(new String(new byte[]{-13,-66,-115,-128}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-93}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-65}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-68}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-71}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-70}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-94}, 0, 4));
//		add(new String(new byte[]{-13,-66,-115,-127}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-96}, 0, 4));
//		add(new String(new byte[]{-13,-66,-116,-67}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-116}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-114}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-124}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-109}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-108}, 0, 4));
//		add(new String(new byte[]{-13,-66,-83,-96}, 0, 4));
//		add(new String(new byte[]{-13,-66,-82,-109}, 0, 4));
//		add(new String(new byte[]{-13,-66,-82,-108}, 0, 4));
//		add(new String(new byte[]{-13,-66,-82,-107}, 0, 4));
//		add(new String(new byte[]{-13,-66,-82,-105}, 0, 4));
//		add(new String(new byte[]{-13,-66,-82,-106}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-109}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-112}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-111}, 0, 4));
//		add(new String(new byte[]{-13,-66,-107,-109}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-103}, 0, 4));
//		add(new String(new byte[]{-13,-66,-83,-99}, 0, 4));
//		add(new String(new byte[]{-13,-66,-83,-101}, 0, 4));
//		add(new String(new byte[]{-13,-66,-83,-103}, 0, 4));
//		add(new String(new byte[]{-13,-66,-83,-105}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-128}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-126}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-127}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-125}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-108}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-124}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-123}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-72}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-125}, 0, 4));
//		add(new String(new byte[]{-13,-66,-127,-128}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-67}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-65}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-68}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-72}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-73}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-65}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-66}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-70}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-68}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-67}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-112}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-111}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-111}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-78}, 0, 4));
//		add(new String(new byte[]{-13,-66,-82,-123}, 0, 4));
//		add(new String(new byte[]{-13,-66,-83,-106}, 0, 4));
//		add(new String(new byte[]{-13,-66,-83,-104}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-108}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-111}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-109}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-106}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-110}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-107}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-105}, 0, 4));	
//		add(new String(new byte[]{-13,-66,-90,-128}, 0, 4));
//		add(new String(new byte[]{-13,-66,-90,-125}, 0, 4));
//		add(new String(new byte[]{-13,-66,-90,-126}, 0, 4));
//		add(new String(new byte[]{-13,-66,-90,-123}, 0, 4));
//		add(new String(new byte[]{-13,-66,-90,-124}, 0, 4));
//		add(new String(new byte[]{-13,-66,-90,-127}, 0, 4));
//		add(new String(new byte[]{-13,-66,-91,-94}, 0, 4));
//		add(new String(new byte[]{-13,-66,-91,-95}, 0, 4));
//		add(new String(new byte[]{-13,-66,-91,-92}, 0, 4));
//		add(new String(new byte[]{-13,-66,-91,-96}, 0, 4));
//		add(new String(new byte[]{-13,-66,-127,-111}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-85}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-92}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-91}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-90}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-97}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-94}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-88}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-86}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-87}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-113}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-116}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-106}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-113}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-123}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-80}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-121}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-91}, 0, 4));
//		add(new String(new byte[]{-13,-66,-122,-107}, 0, 4));
//		add(new String(new byte[]{-13,-66,-82,-126}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-107}, 0, 4));
//		add(new String(new byte[]{-13,-66,-107,-122}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-89}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-66}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-91}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-72}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-85}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-90}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-91}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-93}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-88}, 0, 4));
//		add(new String(new byte[]{-13,-66,-109,-81}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-128}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-125}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-99}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-127}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-120}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-124}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-98}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-97}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-110}, 0, 4));
//		add(new String(new byte[]{-13,-66,-110,-80}, 0, 4));	
//		add(new String(new byte[]{-13,-66,-110,-78}, 0, 4));
//		add(new String(new byte[]{-13,-66,-110,-77}, 0, 4));
//		add(new String(new byte[]{-13,-66,-110,-75}, 0, 4));
//		add(new String(new byte[]{-13,-66,-110,-74}, 0, 4));
//		add(new String(new byte[]{-13,-66,-110,-76}, 0, 4));
//		add(new String(new byte[]{-13,-66,-110,-71}, 0, 4));
//		add(new String(new byte[]{-13,-66,-110,-70}, 0, 4));
//		add(new String(new byte[]{-13,-66,-110,-73}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-70}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-75}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-73}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-93}, 0, 4));
//		add(new String(new byte[]{-13,-66,-97,-74}, 0, 4));
//		add(new String(new byte[]{-13,-66,-108,-122}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-96}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-89}, 0, 4));
//		add(new String(new byte[]{-13,-66,-83,-124}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-102}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-100}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-101}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-99}, 0, 4));
//		add(new String(new byte[]{-13,-66,-85,-80}, 0, 4));
//		add(new String(new byte[]{-13,-66,-85,-78}, 0, 4));
//		add(new String(new byte[]{-13,-66,-85,-79}, 0, 4));
//		add(new String(new byte[]{-13,-66,-85,-77}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-82}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-81}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-80}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-79}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-78}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-77}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-76}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-75}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-74}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-73}, 0, 4));
//		add(new String(new byte[]{-13,-66,-96,-84}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-85}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-84}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-83}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-82}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-81}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-80}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-79}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-78}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-77}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-76}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-75}, 0, 4));
//		add(new String(new byte[]{-13,-66,-128,-74}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-89}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-74}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-79}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-81}, 0, 4));
//		add(new String(new byte[]{-13,-66,-82,-127}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-85}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-87}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-83}, 0, 4));
//		add(new String(new byte[]{-13,-66,-84,-86}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-65}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-66}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-67}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-68}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-70}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-80}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-87}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-89}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-90}, 0, 4));
		add(new String(new byte[]{-13,-66,-116,-96}, 0, 4));
		add(new String(new byte[]{-13,-66,-107,-109}, 0, 4));
		add(new String(new byte[]{-13,-66,-107,-122}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-110}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-111}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-112}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-113}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-122}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-66}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-72}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-85}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-88}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-89}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-90}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-91}, 0, 4));
		add(new String(new byte[]{-13,-66,-108,-93}, 0, 4));
		add(new String(new byte[]{-13,-66,-109,-106}, 0, 4));
		add(new String(new byte[]{-13,-66,-109,-111}, 0, 4));
		add(new String(new byte[]{-13,-66,-109,-113}, 0, 4));
		add(new String(new byte[]{-13,-66,-109,-125}, 0, 4));
		add(new String(new byte[]{-13,-66,-109,-78}, 0, 4));
		add(new String(new byte[]{-13,-66,-109,-80}, 0, 4));
		add(new String(new byte[]{-13,-66,-109,-81}, 0, 4));
		add(new String(new byte[]{-13,-66,-109,-99}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-70}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-71}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-73}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-74}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-75}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-76}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-77}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-78}, 0, 4));
		add(new String(new byte[]{-13,-66,-110,-80}, 0, 4));
		add(new String(new byte[]{-13,-66,-115,-121}, 0, 4));
		add(new String(new byte[]{-13,-66,-115,-124}, 0, 4));
		add(new String(new byte[]{-13,-66,-115,-125}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-107}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-111}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-112}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-65}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-66}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-68}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-70}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-72}, 0, 4));
		add(new String(new byte[]{-13,-66,-122,-73}, 0, 4));
		add(new String(new byte[]{-13,-66,-127,-111}, 0, 4));
		add(new String(new byte[]{-13,-66,-127,-128}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-65}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-108}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-120}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-121}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-123}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-124}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-125}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-126}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-127}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-128}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-67}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-68}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-72}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-74}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-75}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-76}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-77}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-78}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-79}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-80}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-81}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-82}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-83}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-84}, 0, 4));
		add(new String(new byte[]{-13,-66,-128,-85}, 0, 4));
		add(new String(new byte[]{-13,-66,-82,-105}, 0, 4));
		add(new String(new byte[]{-13,-66,-82,-106}, 0, 4));
		add(new String(new byte[]{-13,-66,-82,-107}, 0, 4));
		add(new String(new byte[]{-13,-66,-82,-108}, 0, 4));
		add(new String(new byte[]{-13,-66,-82,-109}, 0, 4));
		add(new String(new byte[]{-13,-66,-82,-123}, 0, 4));
		add(new String(new byte[]{-13,-66,-82,-126}, 0, 4));
		add(new String(new byte[]{-13,-66,-83,-101}, 0, 4));
		add(new String(new byte[]{-13,-66,-83,-103}, 0, 4));
		add(new String(new byte[]{-13,-66,-83,-104}, 0, 4));
		add(new String(new byte[]{-13,-66,-83,-105}, 0, 4));
		add(new String(new byte[]{-13,-66,-83,-106}, 0, 4));
		add(new String(new byte[]{-13,-66,-83,-96}, 0, 4));
		add(new String(new byte[]{-13,-66,-83,-99}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-100}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-101}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-102}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-114}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-116}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-124}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-74}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-79}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-81}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-85}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-93}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-96}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-97}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-98}, 0, 4));
		add(new String(new byte[]{-13,-66,-84,-99}, 0, 4));
		add(new String(new byte[]{-13,-66,-85,-77}, 0, 4));
		add(new String(new byte[]{-13,-66,-85,-78}, 0, 4));
		add(new String(new byte[]{-13,-66,-85,-79}, 0, 4));
		add(new String(new byte[]{-13,-66,-85,-80}, 0, 4));
		add(new String(new byte[]{-13,-66,-90,-123}, 0, 4));
		add(new String(new byte[]{-13,-66,-90,-124}, 0, 4));
		add(new String(new byte[]{-13,-66,-90,-125}, 0, 4));
		add(new String(new byte[]{-13,-66,-90,-126}, 0, 4));
		add(new String(new byte[]{-13,-66,-90,-127}, 0, 4));
		add(new String(new byte[]{-13,-66,-90,-128}, 0, 4));
		add(new String(new byte[]{-13,-66,-91,-92}, 0, 4));
		add(new String(new byte[]{-13,-66,-91,-93}, 0, 4));
		add(new String(new byte[]{-13,-66,-91,-94}, 0, 4));
		add(new String(new byte[]{-13,-66,-91,-95}, 0, 4));
		add(new String(new byte[]{-13,-66,-91,-96}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-100}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-108}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-109}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-120}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-121}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-124}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-125}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-127}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-128}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-91}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-93}, 0, 4));
		add(new String(new byte[]{-13,-66,-96,-99}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-103}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-105}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-106}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-107}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-108}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-109}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-110}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-111}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-70}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-73}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-74}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-75}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-85}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-86}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-87}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-88}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-90}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-92}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-96}, 0, 4));
		add(new String(new byte[]{-13,-66,-97,-97}, 0, 4));
	}};
	
	
	

}
