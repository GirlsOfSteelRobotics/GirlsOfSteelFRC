package com.gos.shuffleboard_codelab.sd_widgets.ss.data;

import com.gos.shuffleboard_codelab.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class CodelabSuperStructureDataType  extends ComplexDataType<CodelabSuperStructureData> {

    public CodelabSuperStructureDataType() {
        super(SmartDashboardNames.SUPER_STRUCTURE_TABLE_NAME, CodelabSuperStructureData.class);
    }

    @Override
    public Function<Map<String, Object>, CodelabSuperStructureData> fromMap() {
        return CodelabSuperStructureData::new;
    }

    @Override
    public CodelabSuperStructureData getDefaultValue() {
        return new CodelabSuperStructureData();
    }
}
