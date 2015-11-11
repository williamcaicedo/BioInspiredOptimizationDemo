package co.bioinspiredoptimizationdemo.main;

import co.bioinspiredoptimizationdemo.functions.AckleyFunction;
import co.bioinspiredoptimizationdemo.functions.AlpineFunction;
import co.bioinspiredoptimizationdemo.functions.EggholderFunction;
import co.bioinspiredoptimizationdemo.functions.GenericFunction;
import co.bioinspiredoptimizationdemo.functions.GriewankFunction;
import co.bioinspiredoptimizationdemo.functions.Levy13Function;
import co.bioinspiredoptimizationdemo.functions.MultiFunction;
import co.bioinspiredoptimizationdemo.functions.PeaksFunction;
import co.bioinspiredoptimizationdemo.functions.RastriginFunction;
import co.bioinspiredoptimizationdemo.functions.RosenbrockFunction;
import co.bioinspiredoptimizationdemo.functions.SchafferFunction;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.camera.CameraMouseController;
import org.jzy3d.chart.controllers.thread.camera.CameraThreadController;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import co.mechanism.core.AbstractOptimizer;
import co.mechanism.core.ICostFunction;
import co.mechanism.core.ISearchAgent;
import co.mechanism.optimizers.evolutionary.de.DEOptimizer;
import co.mechanism.optimizers.evolutionary.genetic.ElitismReplacementProvider;
import co.mechanism.optimizers.evolutionary.genetic.GaussianMutationProvider;
import co.mechanism.optimizers.evolutionary.genetic.GeneticOptimizer;
import co.mechanism.optimizers.evolutionary.genetic.IntermediateRecombinationUniformCrossoverProvider;
import co.mechanism.optimizers.evolutionary.genetic.RandomRealPositionProvider;
import co.mechanism.optimizers.evolutionary.genetic.SUSWithRankSelectionProvider;
import co.mechanism.optimizers.immune.AiNETGaussianMutationProvider;
import co.mechanism.optimizers.immune.ClonalgOptimizer;
import co.mechanism.optimizers.immune.OPTaiNET;
import co.mechanism.optimizers.pso.SwarmOptimizer;
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel chartPanel;
	private JTextArea stats;
	private Chart chart;
	private List<AbstractDrawable> points;
	private List<Double> max;
	private List<Double> min;
	private ICostFunction function;
	private AbstractOptimizer<? extends ISearchAgent> optimizer;
	JButton buttonRefresh;
	JButton buttonScreenshot;
	JButton buttonPSO;
	JButton buttonDE;
	JButton buttonClonalg;
	JButton buttonAiNET;
	private JButton buttonGenetic;
	
	public MainFrame() {
		init();
	}
	
	private void evolve(final AbstractOptimizer<? extends ISearchAgent> optimizer, final int iterations) {
		for (int i = 0; i < iterations; i++) {
			optimizer.evolve();
			refreshChart(optimizer.getPopulation());
		}
		stats.append(optimizer.toString());
		
		
//		Thread t = new Thread() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				for (int i = 0; i < iterations; i++) {
//					optimizer.evolve();
//					SwingUtilities.invokeLater(new Runnable(){
//
//						@Override
//						public void run() {
//							refreshChart(optimizer.getPopulation());
//						}
//						
//					});
//				}
//				SwingUtilities.invokeLater(new Runnable(){
//
//					@Override
//					public void run() {
//						stats.append(optimizer.toString());
//					}
//					
//				});
//			}
//		};
//		
//		t.start();
//		
	}
	
	protected void evolveClonalg() {
		// TODO Auto-generated method stub
		if (this.optimizer == null || !(optimizer instanceof ClonalgOptimizer)) {
			this.optimizer = new ClonalgOptimizer(2, 20, 100, 0.3, min, max, this.function,
					new AiNETGaussianMutationProvider(), new RandomRealPositionProvider(),true);
		}
		AbstractOptimizer<? extends ISearchAgent> optimizer = new ClonalgOptimizer(2, 20, 100, 0.3, min, max, this.function,
				new AiNETGaussianMutationProvider(), new RandomRealPositionProvider(),true);
		evolve(optimizer, 200);
	}

	protected void evolveAiNET() {
		// TODO Auto-generated method stub
		AbstractOptimizer<? extends ISearchAgent> optimizer = new OPTaiNET(2, 20, 100, 0.3, min, max,this.function,
				new AiNETGaussianMutationProvider(), new RandomRealPositionProvider(), true);
		evolve(optimizer, 10);
	}

	protected void evolvePSO() {
		// TODO Auto-generated method stub
		SwarmOptimizer optimizer = new SwarmOptimizer(20, 2, max, min, 5,this.function);
		evolve(optimizer, 300);
	}

	protected void evolveDE() {
		// TODO Auto-generated method stub
		AbstractOptimizer<? extends ISearchAgent> optimizer = new DEOptimizer(2, 20, 0.7, 0.9, min, max,this.function, true);
		evolve(optimizer, 200);

	}

	protected void evolveGenetic() {
		AbstractOptimizer<? extends ISearchAgent> optimizer = new GeneticOptimizer(70, 0.5, min, max,this.function,
				new SUSWithRankSelectionProvider(2, 40),
				new IntermediateRecombinationUniformCrossoverProvider(),
				new ElitismReplacementProvider(),
				new GaussianMutationProvider(0.4), new RandomRealPositionProvider());
		evolve(optimizer, 200);
	}
	
	private Chart getChart(ICostFunction costFunction) {
		Mapper mapper = new MyMapper(function);
		Range xrange = new Range(min.get(0), max.get(0));
		Range yrange = new Range(min.get(1), max.get(1));
        int steps = 50;

        // Create the object to represent the function over the given range.
        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(xrange, steps, yrange, steps), mapper);
        surface.setColorMapper(
        		new ColorMapper(
        				new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), 
        				new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(true);
        surface.setWireframeColor(Color.BLACK);

        // Create a chart and add surface
        Chart chart = new Chart(Quality.Nicest, "awt");
        chart.getScene().getGraph().add(surface);
        
        CameraMouseController mouse = new CameraMouseController();
        chart.addController(mouse);
        CameraThreadController thread = new CameraThreadController();
        mouse.addSlaveThreadController(thread);
        chart.addController(thread);
        chart.render();
        return chart;
		
	}
	
	public void refreshChart(List<? extends ISearchAgent> agents) {
		Iterator<AbstractDrawable> it = chart.getScene().getGraph().getAll()
				.iterator();
		AbstractDrawable p = null;
		while (it.hasNext()) {
			p = it.next();
			if (p instanceof Point) {
				it.remove();
			}

		}
		this.points.clear();
		for (ISearchAgent agent : agents) {
			Point pt = new Point(new Coord3d(agent.getPosition().get(0), agent
					.getPosition().get(1), agent.getValue()), Color.BLACK);
			pt.setWidth(8);
			this.points.add(pt);

		}
		chart.getScene().getGraph().add(points);

	}
	
	private void setBounds(double xmin, double xmax, double ymin, double ymax) {
		max = new ArrayList<Double>();
		min = new ArrayList<Double>();
		max.add(xmax);
		max.add(ymax);
		min.add(xmin);
		min.add(ymin);
	}
	
	private void graph() {
		if (chartPanel.getComponents().length > 0) {
			chartPanel.removeAll();
		}
		this.points = new ArrayList<AbstractDrawable>();
		chart = getChart(this.function);
		optimizer = null;
		chartPanel.setLayout(new java.awt.BorderLayout());
		chartPanel.add((Component)chart.getCanvas());

		chartPanel.revalidate();

	}
	
	protected void getScreenshot() {
		// TODO Auto-generated method stub
		JFileChooser chooser = new JFileChooser();
		// chooser.setFileFilter();
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
			try {
				ImageIO.write(chart.screenshot(), "png",
						chooser.getSelectedFile());
			} catch (Exception e) {
				System.out.println(e);
			}

	}
	
	private void init() {
		
		try {
			// TODO code application logic here
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		//Settings.getInstance().setHardwareAccelerated(true);
		this.setTitle("BioInspired Optimization Demo");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setSize(1100, 700);
		this.setLayout(new BorderLayout());
		JScrollPane westPane = new JScrollPane();
		westPane.setPreferredSize(new Dimension((int) (this.getWidth() * 0.3),
				this.getHeight()));
		westPane.setBorder(BorderFactory.createTitledBorder("Stats"));
		stats = new JTextArea();
		// stats.setColumns(100);
		westPane.setViewportView(stats);
		this.add(westPane, BorderLayout.WEST);
		chartPanel = new JPanel();
		chartPanel.setBorder(BorderFactory.createTitledBorder("View"));
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		center.setSize(500, 600);
		center.add(chartPanel, BorderLayout.CENTER);
		
		/*** Optimizer Buttons ****/
		JPanel buttonPanel = new JPanel();
		buttonRefresh = new JButton("Refresh");
		buttonRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graph();
			}
		});
		buttonScreenshot = new JButton("Screenshot");
		buttonScreenshot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getScreenshot();
			}
		});
		buttonPSO = new JButton("PSO");
		buttonPSO.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evolvePSO();
			}
		});
		buttonDE = new JButton("DE");
		buttonDE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evolveDE();
			}
		});
		buttonClonalg = new JButton("Clonalg");
		buttonClonalg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evolveClonalg();
			}
		});
		buttonAiNET = new JButton("aiNET");
		buttonAiNET.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evolveAiNET();
			}
		});
		buttonGenetic = new JButton("Genetic");
		buttonGenetic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				evolveGenetic();
			}
		});
		buttonPanel.setLayout(new GridLayout(1, 0, 3, 3));
		buttonPanel.add(buttonRefresh);
		buttonPanel.add(buttonScreenshot);
		buttonPanel.add(buttonPSO);
		buttonPanel.add(buttonDE);
		buttonPanel.add(buttonClonalg);
		buttonPanel.add(buttonAiNET);
		buttonPanel.add(buttonGenetic);
		center.add(buttonPanel, BorderLayout.SOUTH);
		this.add(center, BorderLayout.CENTER);

		/**** Menu options ****/
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Graph");
		JMenuItem alpine = new JMenuItem("Alpine");
		alpine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new AlpineFunction();
				setBounds(-10d, 10d, -10d, 10d);
				graph();
			}
		});
		menu.add(alpine);
		JMenuItem ackley = new JMenuItem("Ackley");
		ackley.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new AckleyFunction();
				setBounds(-40d, 40d, -40d, 40d);
				graph();
			}
		});
		menu.add(ackley);
		JMenuItem rastrigin = new JMenuItem("Rastrigin");
		rastrigin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new RastriginFunction();
				setBounds(-5d, 5d, -5d, 5d);
				graph();
			}
		});
		menu.add(rastrigin);
		JMenuItem peaks = new JMenuItem("Peaks");
		peaks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new PeaksFunction();
				setBounds(-3d, 3d, -3d, 3d);
				graph();
			}
		});
		menu.add(peaks);
		JMenuItem griewank = new JMenuItem("Griewank");
		griewank.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new GriewankFunction();
				setBounds(0d, 100d, 0d, 100d);
				graph();
			}
		});
		menu.add(griewank);
		JMenuItem rosenbrock = new JMenuItem("Rosenbrock");
		rosenbrock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new RosenbrockFunction();
				setBounds(-10d, 10d, -10d, 10d);
				graph();
			}
		});
		menu.add(rosenbrock);
		JMenuItem multifunction = new JMenuItem("Multifunction");
		multifunction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new MultiFunction();
				setBounds(-2d, 2d, -2d, 2d);
				graph();
			}
		});
		menu.add(multifunction);
		JMenuItem schaffer = new JMenuItem("Schaffer");
		schaffer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new SchafferFunction();
				setBounds(-10d, 10d, -10d, 10d);
				graph();
			}
		});
		menu.add(schaffer);
		
		JMenuItem levy13 = new JMenuItem("Levy #13");
		levy13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new Levy13Function();
				setBounds(-10d, 10d, -10d, 10d);
				graph();
			}
		});
		menu.add(levy13);
		JMenuItem eggHolder = new JMenuItem("Eggholder");
		eggHolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				function = new EggholderFunction();
				setBounds(-512d, 512d, -512d, 512d);
				graph();
			}
		});
		menu.add(eggHolder);
		
		JMenuItem generic = new JMenuItem("User Function...");
		generic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String f = (String)JOptionPane.showInputDialog(MainFrame.this, "Please specify your function","");
				if (f != null && f != "") {
					function = new GenericFunction(f);
					setBounds(-20d, 20d, -20d, 20d);
					graph();
				}
			}
		});
		menu.add(generic);
		menuBar.add(menu);
		// this.add(menu,BorderLayout.NORTH);
		this.setJMenuBar(menuBar);
	}
	
	public static void main(String[] args) {
		new MainFrame().setVisible(true);
	}
}
