import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private ToDoList toDoList;
    private DefaultListModel<String> listModel;

    public Main() {
        toDoList = new ToDoList();
        listModel = new DefaultListModel<>();
        toDoList.loadFromFile("tasks.txt");
        updateListModel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainApp = new Main();
            mainApp.createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("My To-Do List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JList<String> taskList = new JList<>(listModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(taskList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField taskField = new JTextField(20);
        taskField.setFont(new Font("Arial", Font.PLAIN, 16));
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton completeButton = new JButton("Mark Completed");
        JButton saveButton = new JButton("Save & Exit");

        addButton.setFont(new Font("Arial", Font.PLAIN, 16));
        removeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        completeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(taskField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputPanel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(removeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(completeButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(saveButton, gbc);

        panel.add(inputPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String description = taskField.getText();
                if (!description.isEmpty()) {
                    toDoList.addTask(description);
                    updateListModel();
                    taskField.setText("");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = taskList.getSelectedIndex();
                if (index != -1) {
                    toDoList.removeTask(index);
                    updateListModel();
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = taskList.getSelectedIndex();
                if (index != -1) {
                    toDoList.markTaskCompleted(index);
                    updateListModel();
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toDoList.saveToFile("tasks.txt");
                System.exit(0);
            }
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void updateListModel() {
        listModel.clear();
        for (int i = 0; i < toDoList.getTasks().size(); i++) {
            Task task = toDoList.getTasks().get(i);
            listModel.addElement((i + 1) + ". " + task);
        }
    }
}
