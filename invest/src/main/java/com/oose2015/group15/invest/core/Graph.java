package com.oose2015.group15.invest.core;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Graph represents graph image. Only stocks will have a graph of price trends.
 */
public class Graph {
	BufferedImage graph;
	
	/**
	 * Default constructor for graph
	 * @param filename		image file to be loaded
	 * @throws IOException	
	 */
	public Graph(String filename) throws IOException {
		graph = ImageIO.read(new File(filename));
	}
	
	/**
	 * Displays the graph
	 */
	public void display(Graphics g) {
		
	}
}
