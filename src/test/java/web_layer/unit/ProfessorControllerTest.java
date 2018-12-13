package web_layer.unit;

import bussiness_layer.services.IProfessorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web_layer.BaseControllerTest;
import web_layer.controllers.ProfessorRestController;

public class ProfessorControllerTest extends BaseControllerTest {

    @InjectMocks
    private ProfessorRestController professorRestController;

    @Mock
    private IProfessorService professorService;

    @Before
    public void init() {
        professorRestController = new ProfessorRestController(professorService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(professorRestController)
                .build();
    }

    @Test
    public void givenValidLessons_whenPutLessons_thenLessonsSuccessfullyUpdated() throws Exception {
//        doNothing().when(professorService).updateLessons(any(), any());
//        mockMvc.perform(put("/app/professor/lessons").contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk());
    }
}
