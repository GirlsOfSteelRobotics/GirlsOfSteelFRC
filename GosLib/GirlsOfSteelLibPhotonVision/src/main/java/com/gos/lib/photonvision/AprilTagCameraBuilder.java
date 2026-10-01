package com.gos.lib.photonvision;

import com.gos.lib.field.AprilTagCameraObject.DebugConfig;
import com.gos.lib.field.BaseGosField;
import com.gos.lib.properties.TunableTransform3d;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;

public class AprilTagCameraBuilder {
    private Matrix<N3, N1> m_singleTagStddev = AprilTagCamera.DEFAULT_SINGLE_TAG_STDDEV;
    private Matrix<N3, N1> m_multiTagStddev = AprilTagCamera.DEFAULT_MULTI_TAG_STDDEV;
    private AprilTagFieldLayout m_tagLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);
    private DebugConfig m_fieldDebugConfig = AprilTagCamera.DEFAULT_DEBUG_CONFIG;
    private double m_singleTagMaxDistanceMeters = AprilTagCamera.DEFAULT_SINGLE_TAG_MAX_DISTANCE;
    private double m_singleTagMaxAmbiguityMeters = AprilTagCamera.DEFAULT_SINGLE_TAG_MAX_AMBIGUITY;
    private boolean m_simEnableRawStream = true;
    private boolean m_simEnableProcessedStream = true;
    private boolean m_simEnableDrawWireframe = true;
    private String m_cameraName;
    private TunableTransform3d m_transform;
    private BaseGosField m_field;

    public AprilTagCameraBuilder withCamera(String name) {
        m_cameraName = name;
        return this;
    }

    public AprilTagCameraBuilder withSingleTagMaxDistanceMeters(double maxDistance) {
        m_singleTagMaxDistanceMeters = maxDistance;
        return this;
    }

    public AprilTagCameraBuilder withSingleTagMaxAmbiguity(double max) {
        m_singleTagMaxAmbiguityMeters = max;
        return this;
    }

    public AprilTagCameraBuilder withFieldDebugConfig(DebugConfig config) {
        m_fieldDebugConfig = config;
        return this;
    }

    public AprilTagCameraBuilder withField(BaseGosField field) {
        m_field = field;
        return this;
    }

    public AprilTagCameraBuilder withLayout(AprilTagFieldLayout layout) {
        m_tagLayout = layout;
        return this;
    }

    public AprilTagCameraBuilder withTransform(TunableTransform3d transform) {
        m_transform = transform;
        return this;
    }

    public AprilTagCameraBuilder withSingleTagStddev(Matrix<N3, N1> singleTagStddev) {
        m_singleTagStddev = singleTagStddev;
        return this;
    }

    public AprilTagCameraBuilder withMultiTagStddev(Matrix<N3, N1> multiTagStddev) {
        m_multiTagStddev = multiTagStddev;
        return this;
    }


    public AprilTagCameraBuilder withSimEnableRawStream(boolean enable) {
        m_simEnableRawStream = enable;
        return this;
    }

    public AprilTagCameraBuilder withSimEnableProcessedStream(boolean enable) {
        m_simEnableProcessedStream = enable;
        return this;
    }

    public AprilTagCameraBuilder withSimEnableDrawWireframe(boolean enable) {
        m_simEnableDrawWireframe = enable;
        return this;
    }

    public AprilTagCamera build() {
        return new AprilTagCamera(
            m_tagLayout,
            m_field,
            m_cameraName,
            m_transform,
            m_singleTagMaxDistanceMeters,
            m_singleTagMaxAmbiguityMeters,
            m_singleTagStddev,
            m_multiTagStddev,
            m_fieldDebugConfig,
            m_simEnableRawStream,
            m_simEnableProcessedStream,
            m_simEnableDrawWireframe);
    }

}
