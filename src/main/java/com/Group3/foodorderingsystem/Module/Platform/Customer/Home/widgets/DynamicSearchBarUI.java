package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicSearchBarUI<T> extends VBox {

    private TextField searchField;
    private Button clearButton;
    private VBox resultContainer;
    private VBox defaultUI;
    private List<T> items;
    private String fieldToSearch;
    private RenderTemplate<T> renderTemplate;

    /**
     * @param items
     * @param fieldToSearch
     * @param defaultUI
     * @param renderTemplate
     */
    public DynamicSearchBarUI(List<T> items, String fieldToSearch, Node defaultUI, RenderTemplate<T> renderTemplate) {
        this.items = items;
        this.fieldToSearch = fieldToSearch;
        this.defaultUI = defaultUI != null ? new VBox(defaultUI) : null;
        this.renderTemplate = renderTemplate;
        this.resultContainer = new VBox(5);

        initialize();
    }

    private void initialize() {
        searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-padding: 10px; -fx-font-size: 14px;");
        searchField.setMinHeight(40);

        clearButton = new Button("X");
        clearButton.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: gray; -fx-font-size: 16px; -fx-cursor: hand;");
        clearButton.setMinHeight(40);

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateUI(newValue);
            }
        });

        clearButton.setOnAction(e -> {
            searchField.clear();
            updateUI("");
        });

        HBox searchBar = new HBox(10, searchField, clearButton);
        searchBar.setPadding(new Insets(0, 10, 10, 10));

        HBox.setHgrow(searchField, Priority.ALWAYS);
        searchBar.setFillHeight(true);

        this.getChildren().addAll(searchBar, resultContainer);

        updateUI("");
    }

    private void updateUI(String searchText) {
        resultContainer.getChildren().clear();

        if (searchText.isEmpty()) {
            if (defaultUI != null) {
                resultContainer.getChildren().add(defaultUI);
            } else {
                renderFilteredItems("");
            }
        } else {
            renderFilteredItems(searchText);
        }
    }

    private void renderFilteredItems(String searchText) {
        List<T> filteredItems = items.stream()
                .filter(item -> {
                    try {
                        Field field = item.getClass().getDeclaredField(fieldToSearch);
                        field.setAccessible(true);
                        String fieldValue = (String) field.get(item);
                        return fieldValue != null && fieldValue.toLowerCase().contains(searchText.toLowerCase());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toList());

        resultContainer.getChildren().clear();

        if (!filteredItems.isEmpty()) {
            filteredItems.forEach(item -> {
                Node renderedItem = renderTemplate.render(item);
                resultContainer.getChildren().add(renderedItem);
            });
        } else {
            Label noResultsLabel = new Label("No results found.");
            noResultsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #888;");

            VBox noResultsContainer = new VBox(noResultsLabel);
            noResultsContainer.setAlignment(Pos.CENTER);
            noResultsContainer.setPrefWidth(Double.MAX_VALUE);
            resultContainer.getChildren().add(noResultsContainer);
        }
    }

    public interface RenderTemplate<T> {
        Node render(T item);
    }
}
