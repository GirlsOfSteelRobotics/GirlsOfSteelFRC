

import yaml
import os

def main():
    src_dir = os.path.abspath("src/main/java")
    config = yaml.load(open("dashboard.yml", 'r'), Loader=yaml.SafeLoader)

    for child in config['children_tables']:
        print("Running for %s" % child['table'])
        package_name_as_dir = child["package_name"].replace(".", "/")
        data_dir = os.path.join(src_dir, package_name_as_dir, "data")
        data_path = os.path.join(data_dir, child["table"] + "Data.java")
        data_type_path = os.path.join(data_dir, child["table"] + "DataType.java")

        variable_defs = ""
        base_constructor_args = ""
        map_constructor_args = ""
        populate_map = ""
        accessors = ""
        full_constructor_args = ""
        contructor_init = ""
        changed = ""

        default_lookup = {}
        default_lookup["Double"] = "0.0"
        default_lookup["double"] = "0.0"
        default_lookup["Boolean"] = "false"
        default_lookup["boolean"] = "false"

        for entry in child["entries"]:
            type_as_object = entry["type"][0].upper() + entry["type"][1:]
            upper_case_name = entry["name"][0].upper() + entry["name"][1:]
            variable_defs += "    private final %s m_%s;\n" % (entry["type"], entry["name"])
            base_constructor_args += default_lookup[entry["type"]] + ", "
            map_constructor_args += "(%s) map.getOrDefault(prefix + \"/\" + SmartDashboardNames.%s, %s), " % (type_as_object, entry["dasboard_constant"], default_lookup[entry["type"]])
            populate_map += "        map.put(prefix + SmartDashboardNames.%s, m_%s);\n" % (entry["dasboard_constant"], entry["name"])
            accessors += "    public %s get%s() {\n        return m_%s;\n    }\n\n" % (entry["type"], upper_case_name, entry["name"])
            full_constructor_args += "%s %s, " % (entry["type"], entry["name"])
            contructor_init += "        m_%s = %s;\n" % (entry['name'], entry['name'])
            changed += "        changed |= changes.containsKey(prefix + SmartDashboardNames.%s);\n" % (entry["dasboard_constant"])

        base_constructor_args = base_constructor_args[:-2]
        full_constructor_args = full_constructor_args[:-2]
        map_constructor_args = map_constructor_args[:-2]
        contructor_init = contructor_init[:-1]
        populate_map = populate_map[:-1]
        accessors = accessors[:-1]
        data_dump = DATA_TEMPLATE.format(package_name=child["package_name"],
                                         table_name=child["table"],
                                         full_table_name=child['table_name'],
                                         variable_defs=variable_defs,
                                         base_constructor_args=base_constructor_args,
                                         map_constructor_args=map_constructor_args,
                                         populate_map=populate_map,
                                         accessors=accessors,
                                         full_constructor_args=full_constructor_args,
                                         contructor_init=contructor_init,
                                         changed=changed)

        data_type_dump = DATA_TYPE_TEMPLATE.format(package_name=child["package_name"],
                                                   sd_table_name_constant=child["table_name"],
                                                   table_name=child["table"])

        open(data_path, 'w').write(data_dump)
        open(data_type_path, 'w').write(data_type_dump)
        # print(data_type_dump)
        # print(child)
    # print(config)
    pass


DATA_TEMPLATE = """package {package_name}.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexData;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("PMD.DataClass")
public class {table_name}Data extends ComplexData<{table_name}Data> {{

{variable_defs}

    public {table_name}Data() {{
        this({base_constructor_args});
    }}

    public {table_name}Data(Map<String, Object> map) {{
        this("", map);
    }}

    public {table_name}Data(String prefix, Map<String, Object> map) {{
        this({map_constructor_args});
    }}

    public {table_name}Data({full_constructor_args}) {{
{contructor_init}
    }}

    @Override
    public Map<String, Object> asMap() {{
        return asMap("");
    }}

    public Map<String, Object> asMap(String prefix) {{
        Map<String, Object> map = new HashMap<>();
{populate_map}
        return map;
    }}

    public static boolean hasChanged(Map<String, Object> changes) {{
        return hasChanged(SmartDashboardNames.{full_table_name} + "/", changes);
    }}

    public static boolean hasChanged(String prefix, Map<String, Object> changes) {{
        boolean changed = false;
{changed}
        return changed;
    }}

{accessors}}}
"""

DATA_TYPE_TEMPLATE = """package {package_name}.data;

import com.gos.infinite_recharge.sd_widgets.SmartDashboardNames;
import edu.wpi.first.shuffleboard.api.data.ComplexDataType;

import java.util.Map;
import java.util.function.Function;

public class {table_name}DataType extends ComplexDataType<{table_name}Data> {{

    public {table_name}DataType() {{
        super(SmartDashboardNames.{sd_table_name_constant}, {table_name}Data.class);
    }}

    @Override
    public Function<Map<String, Object>, {table_name}Data> fromMap() {{
        return {table_name}Data::new;
    }}

    @Override
    public {table_name}Data getDefaultValue() {{
        return new {table_name}Data();
    }}
}}
"""


if __name__ == "__main__":
    main()