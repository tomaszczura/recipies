# Recipes
The app shows a list of the first 50 recipes in the godt [api](http://www.godt.no/api/getRecipesListDetailed?tags=&size=thumbnail-medium&ratio=1&limit=50&from=0), containing title, image, description and ingredient names.
App can also work offline (of course, when you run it at least once with network connection).
User can search recipe by it's title or any ingredient it contains.

##App screens

### 1. List screen

The first scren user sees, is the list of recipes. Every item on the list contains image, title and three lines of description.

### 2. Details screen

When user clicks on the recipe, details screen is run. On this screen user also sees image and title of recipe, and, additionally, full description and list of ingredients.

### Recipes search

User can search recipes by title or ingredient. To do this, user have to click search icon on toolbar and start typing any word. App will show suggestions, consisting of full recipes titles and ingredients which matches entered phrase. 


##About app

#### Minimum sdk version: Android 4.1

###Used libraries

1. Retrofit 2
2. Butterknife
3. Dagger 2
4. Glide 
5. ActiveAndroid
6. Android support: RecyclerView, CardView

###App architecture

Application is created in MVP pattern (at least, I try to do this) and with little use of Dagger 2 - for inject RestApi and Presenter to the activity.

####Obtaining and presenting data

When user launch app, it tries to download recipes from server. When it fails because of no network, recipes are loaded from database. 

```java
private Callback<List<Recipe>> getRecipesCallback = new Callback<List<Recipe>>() {

        @Override
        public void onResponse(Response<List<Recipe>> response) {
            recipes = response.body();
            saveService.execute(new RecipeSaveTask(recipes));

            insertRecipesList();
        }

        @Override
        public void onFailure(Throwable t) {
            recipes = Recipe.getAllRecipes();

            insertRecipesList();
        }
    };
    
```
RecipesListPresenter.java

When data are successfully downloaded, RecipesListPresenter start a thread, which save data to database. When thread is send to start, the presenter loads data to activity.

```java
private void insertRecipesList() {
        view.hideLoading();
        view.bindRecipeList(recipes);
}
```
RecipesListPresenter.java

Next, activity creates adapter for RecyclerView and shows data to user.

```java
public void bindRecipeList(List<Recipe> recipes) {
        recipeListAdapter = new RecipeListAdapter(this, recipes, onRecipeClick);
        recipesListView.setAdapter(recipeListAdapter);
}
```
MainActivity.java

When user clicks any recipe, RecipeActivity is launched. MainActivity pass clicked recipe id in the intent, and in RecipeActivity the recipe is selected from database according to this id.


####Searching recipes

When user click search icon on toolbar, search view appears. Then, when user starts typing, there are search suggestions. These suggestions might be recipe title or ingredient name.
Application first search for recipes with title which contains entered phrase, and next, any ingredient (Item) which name contains phrase. Then loads it into cursor along with type (Recipe or Item) and passes to search view.

```java
private void prepareSuggestions(String query) {
        final MatrixCursor cursor = new MatrixCursor(new String[]{ BaseColumns._ID, Constants.MODEL_NAME_KEY, Constants.SUGGESTION_RESULT_KEY});

        List<Recipe> recipes = Recipe.withName(query);
        List<Item> items = Item.withName(query);

        long id = 0;
        for (Recipe recipe : recipes) {
            cursor.addRow(new Object[] {id, Recipe.class.getSimpleName(), recipe.getTitle()});
            id++;
        }

        for (Item item : items) {
            cursor.addRow(new Object[] {id, Item.class.getSimpleName(), item.getName()});
            id++;
        }

        view.insertSuggestions(cursor);
}
```
CharacterListPresenter.java

When user clicks suggestion with recipe title, the recipe with that (exact) title is selected from database. 
When he clicks suggestion with ingredient name, database is searched for recipes which contains that (exact) ingredient.

User may also click 'Search' button on keyboard, and then app shows recipes which titles contains entered phrase and recipes with ingredients which names contains entered phrase.



