package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import dataload.RawFileLineLoader;
import datamodel.buildingblocks.LineBlock;
import datamodel.ruleset.RuleSet;
import engine.Engine;

public class Tests {

	@Test
	public void RegisterRulesTest() {

		Engine engine = new Engine("Resources/SampleDocs/hippocratesOath.txt", "RAW", "hippo");
		
		List<List<String>> inputSpec = new ArrayList<List<String>>();
		ArrayList<String> h1List = new ArrayList<String>(); inputSpec.add(h1List);
		h1List.add("H1");h1List.add("STARTS_WITH"); h1List.add("OATH AND");
		ArrayList<String> omList = new ArrayList<String>(); inputSpec.add(omList);
		omList.add("OMIT");omList.add("POSITIONS");omList.add("0,3");	
		ArrayList<String> h2List = new ArrayList<String>(); inputSpec.add(h2List);
		h2List.add("H2");h2List.add("ALL_CAPS");
		ArrayList<String> italicsList = new ArrayList<String>(); inputSpec.add(italicsList);
		italicsList.add("<I>");italicsList.add("POSITIONS"); italicsList.add("4,16");
		
		RuleSet ruleset = engine.registerInputRuleSetForPlainFiles(inputSpec);
		String ret = "inputRuleSet\nOMIT: RuleInPosition\nH1: RuleStartWith\nH2: RuleAllCaps\nBOLD: RuleUndefined\nITALICS: RuleInPosition\n";
		
		assertEquals(ruleset.toString(), ret);
	}
	
	@Test
	public void ExportMarkdownTest() {

		String inputFile = "Resources/SampleDocs/hippocratesOath.html";
		
		List<List<String>> inputSpec = new ArrayList<List<String>>();
		List<String> h1List = new ArrayList<String>(); inputSpec.add(h1List);
		h1List.add("H1");h1List.add("STARTS_WITH"); h1List.add("<H1>");
		List<String> h2List = new ArrayList<String>(); inputSpec.add(h2List);
		h2List.add("H2");h2List.add("STARTS_WITH"); h2List.add("<H2>");
		List<String> italicsList = new ArrayList<String>(); inputSpec.add(italicsList);
		italicsList.add("<I>");italicsList.add("STARTS_WITH"); italicsList.add("<i>");
		List<String> boldList = new ArrayList<String>(); inputSpec.add(boldList);
		boldList.add("<B>");boldList.add("STARTS_WITH"); boldList.add("<b>");
		
		List<String> prefixes = new ArrayList<String>(); 
		prefixes.add("<H1>");prefixes.add("<H2>");prefixes.add("<i>");prefixes.add("<b>");prefixes.add("<p>");
		
		Engine engine = new Engine(inputFile, "ANNOTATED", "hippo");
		
		engine.registerInputRuleSetForAnnotatedFiles(inputSpec, prefixes);
		engine.loadFileAndCharacterizeBlocks();
		
		String outputFileName = "Resources//Outputs//hippocratesOathHtml.md";
		engine.exportMarkDown(outputFileName);
						
		try {
			FileReader filereader = new FileReader(new File(outputFileName));
			BufferedReader reader = new BufferedReader(filereader);
			
			// Tests the first lines of the created file
			assertEquals(reader.readLine(), "Internet Wiretap Edition of ");
			assertEquals(reader.readLine(), "");
			assertEquals(reader.readLine(), "#OATH AND LAW OF HIPPOCRATES ");
			assertEquals(reader.readLine(), "");
			assertEquals(reader.readLine(), "From \"Harvard Classics Volume 38\" Copyright 1910 by P.F. Collier and Son. ");
			assertEquals(reader.readLine(), "");
			assertEquals(reader.readLine(), "This text is placed in the Public Domain, June 1993. ");
			assertEquals(reader.readLine(), "");
			assertEquals(reader.readLine(), "##INTRODUCTORY NOTE ");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void ReportStatsTest() {
		String inputFile = "Resources/SampleDocs/hippocratesOath.txt";
		
		Engine engine = new Engine(inputFile, "RAW", "hippo");
		engine.loadFileAndCharacterizeBlocks();
		
		List<String> stats = engine.reportWithStats();
		
		assertEquals(stats.get(0), "\nTotal number of paragraphs: 17");
		assertEquals(stats.get(1), "\nTotal number of words: 1145");
		assertEquals(stats.get(2), "\nLines: 1 Words: 4");
		assertEquals(stats.get(3), "\nLines: 1 Words: 5");
		assertEquals(stats.get(4), "\nLines: 2 Words: 12");
		assertEquals(stats.get(5), "\nLines: 1 Words: 10");
		assertEquals(stats.get(6), "\nLines: 1 Words: 2");
		assertEquals(stats.get(7), "\nLines: 11 Words: 112");
		assertEquals(stats.get(8), "\nLines: 8 Words: 86");
		assertEquals(stats.get(9), "\nLines: 7 Words: 76");
		assertEquals(stats.get(10), "\nLines: 1 Words: 4");
		assertEquals(stats.get(11), "\nLines: 30 Words: 365");
		assertEquals(stats.get(12), "\nLines: 1 Words: 4");
		assertEquals(stats.get(13), "\nLines: 10 Words: 121");
		assertEquals(stats.get(14), "\nLines: 10 Words: 107");
		assertEquals(stats.get(15), "\nLines: 8 Words: 93");
		assertEquals(stats.get(16), "\nLines: 10 Words: 107");
		assertEquals(stats.get(17), "\nLines: 3 Words: 36");
		assertEquals(stats.get(18), "\nLines: 1 Words: 1");
	}
	
	@Test
	public void LoadFileTest() {
		String inputFile = "Resources/SampleDocs/hippocratesOath.txt";
		List<LineBlock> lineblocks = new ArrayList<LineBlock>();
		RawFileLineLoader loader = new RawFileLineLoader();
		
		loader.load(inputFile, lineblocks);
		
		// Test for the first lines of the file
		assertEquals(lineblocks.get(0).getLines().get(0), "Internet Wiretap Edition of");
		assertEquals(lineblocks.get(1).getLines().get(0), "OATH AND LAW OF HIPPOCRATES");
		assertEquals(lineblocks.get(2).getLines().get(0), "From \"Harvard Classics Volume 38\"");
		assertEquals(lineblocks.get(2).getLines().get(1), "Copyright 1910 by P.F. Collier and Son.");
		
	}
	
}
