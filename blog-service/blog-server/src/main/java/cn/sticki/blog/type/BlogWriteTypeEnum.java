package cn.sticki.blog.type;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Date 2022/10/25
 *
 * @author likangli
 * description 博客创作类型
 */
public enum BlogWriteTypeEnum {

	/**
	 * 博客创作类型枚举
	 */
	ORIGINAL(1, "原创"),
	REPRINT(2, "转载"),
	;

	/**
	 * 创作类型编码
	 */
	@Getter
	private final Integer code;

	/**
	 * 创作编码描述
	 */
	@Getter
	private final String desc;

	BlogWriteTypeEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 根据创建编码获取枚举类
	 *
	 * @param code 创作编码
	 * @return 创作枚举类Optional对象
	 */
	public static Optional<BlogWriteTypeEnum> getEnum(@Valid @NotNull Integer code) {
		return Stream.of(BlogWriteTypeEnum.values())
				.filter(e -> Objects.equals(e.getCode(), code))
				.findFirst();
	}

}
