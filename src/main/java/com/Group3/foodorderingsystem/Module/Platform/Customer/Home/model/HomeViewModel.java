package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.model;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.HomeUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.MenuSelectionUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.ShopSelection;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.VendorReview;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderSummaryUI;

import javafx.scene.Node;

public class HomeViewModel extends ViewModelConfig {

    @Override
    public void navigate(Node node) {
        setNode(node);
        CustomerViewModel.navigate(CustomerViewModel.getHomeViewModel().getNode());
    }

    public HomeViewModel() {
        super();
        shopSelection = new ShopSelection();
    }

    public void init() {
        this.homeUI = new HomeUI();
        setNode(homeUI);
    }

    private HomeUI homeUI;

    public HomeUI getHomeUI() {
        return homeUI;
    }

    public void setHomeUI(HomeUI homeUI) {
        this.homeUI = homeUI;
    }

    private ShopSelection shopSelection;

    public ShopSelection getShopSelection() {
        return shopSelection;
    }

    public void setShopSelection(ShopSelection shopSelection) {
        this.shopSelection = shopSelection;
    }

    private MenuSelectionUI menuSelectionUI;

    public MenuSelectionUI getMenuSelectionUI() {
        return menuSelectionUI;
    }

    public void setMenuSelectionUI(MenuSelectionUI menuSelectionUI) {
        this.menuSelectionUI = menuSelectionUI;
    }

    public void initMenuSelection(String vendorId) {
        bottomButton = new BottomButton("Add Items", () -> {
        });
        bottomButton.setDisable(true);
        this.menuSelectionUI = new MenuSelectionUI(vendorId);
        navigate(menuSelectionUI);
    }

    private BottomButton bottomButton;

    public BottomButton getBottomButton() {
        return bottomButton;
    }

    public void addProductToCart(ItemModel item, int quantity) {
        List<ItemModel> cartItems = SessionUtil.getItemsFromSession();

        if (cartItems == null) {
            cartItems = new ArrayList<>(); // Initialize an empty list if cart is null
        }

        boolean isItemExist = false;
        for (ItemModel cartItem : cartItems) {
            if (cartItem.getItemId().equals(item.getItemId())) {
                // Update the quantity of the existing item
                if (quantity > 0) {
                    cartItem.setItemQuantity(quantity);
                } else {
                    // Remove the item if quantity is 0 or less
                    cartItems.remove(cartItem);
                }
                isItemExist = true;
                break;
            }
        }

        if (!isItemExist && quantity > 0) {
            // If the item is not already in the cart and the quantity is greater than 0,
            // add it
            item.setItemQuantity(quantity); // Ensure the item quantity is set before adding it
            cartItems.add(item);
        }

        // Save updated cart back to session
        SessionUtil.setItemsInSession(cartItems);

        // Update the bottom button text based on cart content
        updateBottomButtonText(cartItems);
    }

    private void updateBottomButtonText(List<ItemModel> cartItems) {
        int size = 0;
        double price = 0;

        // Check if cartItems is null or empty, prevent unnecessary calculations
        if (cartItems != null && !cartItems.isEmpty()) {
            for (ItemModel item : cartItems) {
                if (item != null) {
                    size += item.getItemQuantity();
                    price += item.getItemPrice() * item.getItemQuantity();
                }
            }
        }

        // Handle the state of the button based on cartItems
        if (cartItems == null || cartItems.isEmpty()) {
            bottomButton.setText("Add Items");
            bottomButton.setAction(() -> {
                // Add functionality when Add Items button is clicked, e.g., navigate to menu
                // selection
                System.out.println("No items in cart, navigate to menu selection");
            });
            bottomButton.setDisabled(true); // Disable button when cart is empty
        } else {
            bottomButton.setText("Check Out (Total of " + size + " Items: RM " + String.format("%.2f", price) + ")");
            bottomButton.setAction(() -> {
                // Handle checkout logic
                System.out.println("Proceeding to Checkout...");
            });
            bottomButton.setDisabled(false); // Enable button when cart has items
        }
    }

    private VendorReview vendorReview;

    public VendorReview getVendorReview() {
        return vendorReview;
    }
    
    public void setVendorReview(VendorReview vendorReview) {
        this.vendorReview = vendorReview;
    }

}
