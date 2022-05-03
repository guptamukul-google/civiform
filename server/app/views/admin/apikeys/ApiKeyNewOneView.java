package views.admin.programs;

import static com.google.common.base.Preconditions.checkNotNull;
import static j2html.TagCreator.div;
import static j2html.TagCreator.form;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import j2html.tags.ContainerTag;
import play.mvc.Http.Request;
import play.twirl.api.Content;
import views.BaseHtmlView;
import views.HtmlBundle;
import views.admin.AdminLayout;
import views.components.FieldWithLabel;

/** Renders a page for adding a new ApiKey. */
public final class ApiKeyNewOneView extends BaseHtmlView {
  private final AdminLayout layout;

  @Inject
  public ApiKeyNewOneView(AdminLayout layout) {
    this.layout = checkNotNull(layout);
  }

  public Content render(Request request, ImmutableList<String> programSlugs) {
    String title = "Create a new API key";

    ContainerTag formTag = form().withMethod("POST");

    ContainerTag contentDiv =
        div(
            formTag
                .with(
                    makeCsrfTokenInputTag(request),
                    FieldWithLabel.input()
                        .setFieldName("keyName")
                        .setLabelText("Name")
                        .getContainer(),
                    submitButton("Save").withId("apikey-submit-button"))
                .withAction(controllers.admin.routes.AdminApiKeysController.create().url()));

    HtmlBundle htmlBundle =
        layout.getBundle().setTitle(title).addMainContent(renderHeader(title), contentDiv);

    return layout.renderCentered(htmlBundle);
  }
}
